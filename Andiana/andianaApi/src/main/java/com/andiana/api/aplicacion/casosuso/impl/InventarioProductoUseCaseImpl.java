package com.andiana.api.aplicacion.casosuso.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.andiana.api.aplicacion.casosuso.entrada.IInventarioProductoUseCase;
import com.andiana.api.aplicacion.util.Validaciones;
import com.andiana.api.dominio.entidades.ControlCalidad;
import com.andiana.api.dominio.entidades.InventarioProducto;
import com.andiana.api.dominio.entidades.LoteProduccion;
import com.andiana.api.dominio.repositorio.IControlCalidadRepositorio;
import com.andiana.api.dominio.repositorio.IInventarioProductoRepositorio;
import com.andiana.api.dominio.repositorio.ILoteProduccionRepositorio;

public class InventarioProductoUseCaseImpl implements IInventarioProductoUseCase {

	private final IInventarioProductoRepositorio repositorio;
	private final ILoteProduccionRepositorio loteRepositorio;
	private final IControlCalidadRepositorio controlRepositorio;

	public InventarioProductoUseCaseImpl(IInventarioProductoRepositorio repositorio,
			ILoteProduccionRepositorio loteRepositorio, IControlCalidadRepositorio controlRepositorio) {
		this.repositorio = repositorio;
		this.loteRepositorio = loteRepositorio;
		this.controlRepositorio = controlRepositorio;
	}

	@Override
	public InventarioProducto guardar(InventarioProducto nuevoInventarioProducto) {
		nuevoInventarioProducto.setUbicacion(Validaciones.normalizar(nuevoInventarioProducto.getUbicacion()));

		Validaciones.obligatorio(nuevoInventarioProducto.getIdLote(), "lote");
		Validaciones.mayorQueCero(nuevoInventarioProducto.getCantidad(), "cantidad");
		Validaciones.obligatorio(nuevoInventarioProducto.getUbicacion(), "ubicación");

		if (nuevoInventarioProducto.getFechaIngreso() == null) {
			nuevoInventarioProducto.setFechaIngreso(LocalDate.now());
		}

		LoteProduccion lote = loteRepositorio.buscarPorid(nuevoInventarioProducto.getIdLote())
				.orElseThrow(() -> new RuntimeException("El lote indicado no existe"));

		String resultado = resultadoVigente(nuevoInventarioProducto.getIdLote());
		if (resultado == null) {
			throw new RuntimeException("El lote " + lote.getNumeroLote()
					+ " todavía no tiene control de calidad: no puede entrar al inventario");
		}
		if (!ControlCalidadUseCaseImpl.APROBADO.equals(resultado)) {
			throw new RuntimeException("El lote " + lote.getNumeroLote() + " está " + resultado
					+ ": solo entran al inventario los lotes aprobados");
		}

		BigDecimal producido = lote.getCantidadProducida() == null ? BigDecimal.ZERO : lote.getCantidadProducida();
		BigDecimal yaGuardado = repositorio.buscarPorLote(nuevoInventarioProducto.getIdLote()).stream()
				.filter(otro -> !otro.getIdInventario().equals(nuevoInventarioProducto.getIdInventario()))
				.map(InventarioProducto::getCantidad).reduce(BigDecimal.ZERO, BigDecimal::add);

		if (yaGuardado.add(nuevoInventarioProducto.getCantidad()).compareTo(producido) > 0) {
			throw new RuntimeException("No caben " + Validaciones.legible(nuevoInventarioProducto.getCantidad())
					+ " unidades: el lote " + lote.getNumeroLote() + " produjo " + Validaciones.legible(producido)
					+ " y ya hay " + Validaciones.legible(yaGuardado) + " guardadas");
		}

		return repositorio.guardar(nuevoInventarioProducto);
	}

	@Override
	public InventarioProducto buscarPorId(int idInventario) {
		return repositorio.buscarPorid(idInventario)
				.orElseThrow(() -> new RuntimeException("Registro de inventario no encontrado"));
	}

	@Override
	public List<InventarioProducto> listarTodos() {
		return repositorio.listarTodos();
	}

	@Override
	public void eliminar(int idInventario) {
		buscarPorId(idInventario);
		repositorio.eliminar(idInventario);
	}

	private String resultadoVigente(int idLote) {
		return controlRepositorio.buscarPorLote(idLote).stream().findFirst().map(ControlCalidad::getResultado)
				.orElse(null);
	}
}
