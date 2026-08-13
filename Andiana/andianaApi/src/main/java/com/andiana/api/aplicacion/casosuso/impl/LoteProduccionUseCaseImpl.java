package com.andiana.api.aplicacion.casosuso.impl;

import java.util.Comparator;
import java.util.List;

import com.andiana.api.aplicacion.casosuso.entrada.ILoteProduccionUseCase;
import com.andiana.api.aplicacion.util.Validaciones;
import com.andiana.api.dominio.entidades.LoteProduccion;
import com.andiana.api.dominio.repositorio.IControlCalidadRepositorio;
import com.andiana.api.dominio.repositorio.IInventarioProductoRepositorio;
import com.andiana.api.dominio.repositorio.ILoteProduccionRepositorio;
import com.andiana.api.dominio.repositorio.IOrdenProduccionRepositorio;

public class LoteProduccionUseCaseImpl implements ILoteProduccionUseCase {

	private final ILoteProduccionRepositorio repositorio;
	private final IOrdenProduccionRepositorio ordenRepositorio;
	private final IControlCalidadRepositorio controlRepositorio;
	private final IInventarioProductoRepositorio inventarioRepositorio;

	public LoteProduccionUseCaseImpl(ILoteProduccionRepositorio repositorio,
			IOrdenProduccionRepositorio ordenRepositorio, IControlCalidadRepositorio controlRepositorio,
			IInventarioProductoRepositorio inventarioRepositorio) {
		this.repositorio = repositorio;
		this.ordenRepositorio = ordenRepositorio;
		this.controlRepositorio = controlRepositorio;
		this.inventarioRepositorio = inventarioRepositorio;
	}

	@Override
	public LoteProduccion guardar(LoteProduccion nuevoLoteProduccion) {
		nuevoLoteProduccion.setNumeroLote(Validaciones.normalizar(nuevoLoteProduccion.getNumeroLote()));
		nuevoLoteProduccion.setEstado(Validaciones.normalizar(nuevoLoteProduccion.getEstado()));

		Validaciones.obligatorio(nuevoLoteProduccion.getNumeroLote(), "número de lote");
		Validaciones.obligatorio(nuevoLoteProduccion.getIdOrden(), "orden de producción");

		if (nuevoLoteProduccion.getEstado() == null) {
			nuevoLoteProduccion.setEstado("EN_PROCESO");
		}
		Validaciones.unoDe(nuevoLoteProduccion.getEstado(), "estado", "EN_PROCESO", "FINALIZADO", "RECHAZADO");

		if (ordenRepositorio.buscarPorId(nuevoLoteProduccion.getIdOrden()).isEmpty()) {
			throw new RuntimeException("La orden de producción indicada no existe");
		}
		Validaciones.noRepetido(repositorio.listarTodos(), LoteProduccion::getIdLote, LoteProduccion::getNumeroLote,
				nuevoLoteProduccion.getIdLote(), nuevoLoteProduccion.getNumeroLote(), "un lote con el número");

		if (nuevoLoteProduccion.getFechaInicio() != null && nuevoLoteProduccion.getFechaFin() != null
				&& nuevoLoteProduccion.getFechaFin().isBefore(nuevoLoteProduccion.getFechaInicio())) {
			throw new RuntimeException("La fecha de fin no puede ser anterior a la de inicio");
		}

		if ("FINALIZADO".equals(nuevoLoteProduccion.getEstado())) {
			Validaciones.mayorQueCero(nuevoLoteProduccion.getCantidadProducida(), "cantidad producida");
		} else if (nuevoLoteProduccion.getCantidadProducida() != null
				&& nuevoLoteProduccion.getCantidadProducida().signum() < 0) {
			throw new RuntimeException("La cantidad producida no puede ser negativa");
		}

		return repositorio.guardar(nuevoLoteProduccion);
	}

	@Override
	public LoteProduccion buscarPorId(int idLote) {
		return repositorio.buscarPorId(idLote)
				.orElseThrow(() -> new RuntimeException("Lote de producción no encontrado"));
	}

	/**
	 * Lo ultimo registrado va arriba. El listado se pagina, asi que en orden
	 * ascendente lo que se acaba de crear cae en la ultima pagina: el usuario
	 * vuelve del formulario, no lo ve, y cree que no se guardo.
	 */
	@Override
	public List<LoteProduccion> listarTodos() {
		return repositorio.listarTodos().stream()
				.sorted(Comparator.comparing(LoteProduccion::getIdLote,
						Comparator.nullsLast(Comparator.reverseOrder())))
				.toList();
	}

	@Override
	public void eliminar(int idLote) {
		if (!inventarioRepositorio.buscarPorLote(idLote).isEmpty()) {
			throw new RuntimeException("No se puede eliminar el lote: ya está guardado en el inventario");
		}
		if (!controlRepositorio.buscarPorLote(idLote).isEmpty()) {
			throw new RuntimeException("No se puede eliminar el lote: ya tiene control de calidad");
		}
		buscarPorId(idLote);
		repositorio.eliminar(idLote);
	}
}
