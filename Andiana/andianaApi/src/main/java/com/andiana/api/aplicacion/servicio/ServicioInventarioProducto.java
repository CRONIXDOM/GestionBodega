package com.andiana.api.aplicacion.servicio;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.Validar;
import com.andiana.api.dominio.modelo.InventarioProducto;
import com.andiana.api.dominio.modelo.LoteProduccion;
import com.andiana.api.dominio.puerto.ControlCalidadRepositorio;
import com.andiana.api.dominio.puerto.InventarioProductoRepositorio;
import com.andiana.api.dominio.puerto.LoteProduccionRepositorio;

/**
 * La regla central del proceso: al inventario de productos terminados solo
 * entra lo que el laboratorio aprobo.
 *
 * Un mismo lote puede repartirse en varias ubicaciones, asi que se admiten
 * varios registros por lote; lo que no puede es guardarse mas de lo que el lote
 * llego a producir.
 */
public class ServicioInventarioProducto extends ServicioCrud<InventarioProducto> {

	private final InventarioProductoRepositorio inventario;
	private final LoteProduccionRepositorio lotes;
	private final ServicioControlCalidad servicioControl;

	public ServicioInventarioProducto(InventarioProductoRepositorio inventario, LoteProduccionRepositorio lotes,
			ControlCalidadRepositorio controles, ServicioControlCalidad servicioControl) {
		super(inventario, "Registro de inventario");
		this.inventario = inventario;
		this.lotes = lotes;
		this.servicioControl = servicioControl;
	}

	@Override
	protected void validar(InventarioProducto registro) {
		registro.setUbicacion(Validar.normalizar(registro.getUbicacion()));

		Validar.obligatorio(registro.getIdLote(), "lote");
		Validar.mayorQueCero(registro.getCantidad(), "cantidad");
		Validar.obligatorio(registro.getUbicacion(), "ubicacion");
		if (registro.getFechaIngreso() == null) {
			registro.setFechaIngreso(LocalDate.now());
		}

		LoteProduccion lote = lotes.buscarPorId(registro.getIdLote())
				.orElseThrow(() -> new ReglaNegocioException("El lote indicado no existe"));

		String resultado = servicioControl.resultadoVigente(registro.getIdLote());
		if (resultado == null) {
			throw new ReglaNegocioException("El lote " + lote.getNumeroLote()
					+ " todavia no tiene control de calidad: no puede entrar al inventario");
		}
		if (!ServicioControlCalidad.APROBADO.equals(resultado)) {
			throw new ReglaNegocioException("El lote " + lote.getNumeroLote() + " esta " + resultado
					+ ": solo entran al inventario los lotes aprobados");
		}

		BigDecimal producido = lote.getCantidadProducida() == null ? BigDecimal.ZERO : lote.getCantidadProducida();
		BigDecimal yaGuardado = inventario.buscarPorLote(registro.getIdLote()).stream()
				.filter(otro -> !otro.getIdInventario().equals(registro.getIdInventario()))
				.map(InventarioProducto::getCantidad)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		if (yaGuardado.add(registro.getCantidad()).compareTo(producido) > 0) {
			throw new ReglaNegocioException("No caben " + Validar.legible(registro.getCantidad())
					+ " unidades: el lote " + lote.getNumeroLote() + " produjo "
					+ Validar.legible(producido) + " y ya hay " + Validar.legible(yaGuardado)
					+ " guardadas");
		}
	}
}
