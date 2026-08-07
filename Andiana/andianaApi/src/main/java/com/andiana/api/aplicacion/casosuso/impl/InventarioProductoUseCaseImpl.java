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

/**
 * La regla central del proceso: al inventario de productos terminados solo
 * entra lo que el laboratorio aprobo.
 *
 * Un mismo lote puede repartirse en varias ubicaciones, asi que se admiten
 * varios registros por lote; lo que no puede es guardarse mas de lo que el lote
 * llego a producir.
 */
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
				.map(InventarioProducto::getCantidad)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		if (yaGuardado.add(nuevoInventarioProducto.getCantidad()).compareTo(producido) > 0) {
			throw new RuntimeException("No caben " + Validaciones.legible(nuevoInventarioProducto.getCantidad())
					+ " unidades: el lote " + lote.getNumeroLote() + " produjo "
					+ Validaciones.legible(producido) + " y ya hay " + Validaciones.legible(yaGuardado)
					+ " guardadas");
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

	/**
	 * El resultado que vale para un lote es el de su control mas reciente: el
	 * laboratorio puede volver a inspeccionarlo despues de una observacion.
	 */
	private String resultadoVigente(int idLote) {
		return controlRepositorio.buscarPorLote(idLote).stream()
				.findFirst()
				.map(ControlCalidad::getResultado)
				.orElse(null);
	}
}
