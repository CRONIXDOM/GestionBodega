package com.andiana.api.aplicacion.casosuso.impl;

import java.util.List;

import com.andiana.api.aplicacion.casosuso.entrada.IOrdenProduccionUseCase;
import com.andiana.api.aplicacion.util.Validaciones;
import com.andiana.api.dominio.entidades.OrdenProduccion;
import com.andiana.api.dominio.repositorio.ILoteProduccionRepositorio;
import com.andiana.api.dominio.repositorio.IOrdenProduccionRepositorio;
import com.andiana.api.dominio.repositorio.IProductoRepositorio;

public class OrdenProduccionUseCaseImpl implements IOrdenProduccionUseCase {

	private final IOrdenProduccionRepositorio repositorio;
	private final IProductoRepositorio productoRepositorio;
	private final ILoteProduccionRepositorio loteRepositorio;

	public OrdenProduccionUseCaseImpl(IOrdenProduccionRepositorio repositorio, IProductoRepositorio productoRepositorio,
			ILoteProduccionRepositorio loteRepositorio) {
		this.repositorio = repositorio;
		this.productoRepositorio = productoRepositorio;
		this.loteRepositorio = loteRepositorio;
	}

	@Override
	public OrdenProduccion guardar(OrdenProduccion nuevoOrdenProduccion) {
		nuevoOrdenProduccion.setEstado(Validaciones.normalizar(nuevoOrdenProduccion.getEstado()));
		nuevoOrdenProduccion.setResponsable(Validaciones.normalizar(nuevoOrdenProduccion.getResponsable()));

		Validaciones.obligatorio(nuevoOrdenProduccion.getIdProducto(), "producto");
		Validaciones.obligatorio(nuevoOrdenProduccion.getFechaProgramada(), "fecha programada");
		Validaciones.mayorQueCero(nuevoOrdenProduccion.getCantidadProgramada(), "cantidad programada");

		if (nuevoOrdenProduccion.getEstado() == null) {
			nuevoOrdenProduccion.setEstado("PLANIFICADA");
		}
		Validaciones.unoDe(nuevoOrdenProduccion.getEstado(), "estado", "PLANIFICADA", "EN_PROCESO", "FINALIZADA",
				"CANCELADA");

		if (productoRepositorio.buscarPorId(nuevoOrdenProduccion.getIdProducto()).isEmpty()) {
			throw new RuntimeException("El producto indicado no existe");
		}

		if ("CANCELADA".equals(nuevoOrdenProduccion.getEstado()) && nuevoOrdenProduccion.getIdOrden() != null
				&& !loteRepositorio.buscarPorOrden(nuevoOrdenProduccion.getIdOrden()).isEmpty()) {
			throw new RuntimeException("No se puede cancelar la orden: ya tiene lotes fabricados");
		}

		return repositorio.guardar(nuevoOrdenProduccion);
	}

	@Override
	public OrdenProduccion buscarPorId(int idOrden) {
		return repositorio.buscarPorId(idOrden)
				.orElseThrow(() -> new RuntimeException("Orden de producción no encontrada"));
	}

	@Override
	public List<OrdenProduccion> listarTodos() {
		return repositorio.listarTodos();
	}

	@Override
	public void eliminar(int idOrden) {
		if (!loteRepositorio.buscarPorOrden(idOrden).isEmpty()) {
			throw new RuntimeException(
					"No se puede eliminar la orden: ya tiene lotes fabricados. Elimina primero los lotes.");
		}
		buscarPorId(idOrden);
		repositorio.eliminar(idOrden);
	}
}
