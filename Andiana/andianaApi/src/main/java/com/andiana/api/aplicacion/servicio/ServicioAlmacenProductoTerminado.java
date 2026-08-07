package com.andiana.api.aplicacion.servicio;

import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.Validar;
import com.andiana.api.dominio.modelo.AlmacenProductoTerminado;
import com.andiana.api.dominio.modelo.ControlCalidad;
import com.andiana.api.dominio.modelo.LoteProduccion;
import com.andiana.api.dominio.puerto.AlmacenProductoTerminadoRepositorio;
import com.andiana.api.dominio.puerto.ControlCalidadRepositorio;
import com.andiana.api.dominio.puerto.LoteProduccionRepositorio;

/**
 * La regla central del proceso: al almacen de productos terminados solo entra
 * lo que el laboratorio aprobo.
 */
public class ServicioAlmacenProductoTerminado extends ServicioCrud<AlmacenProductoTerminado> {

	private final AlmacenProductoTerminadoRepositorio almacen;
	private final LoteProduccionRepositorio lotes;
	private final ControlCalidadRepositorio controles;

	public ServicioAlmacenProductoTerminado(AlmacenProductoTerminadoRepositorio almacen,
			LoteProduccionRepositorio lotes, ControlCalidadRepositorio controles) {
		super(almacen, "Registro de almacen");
		this.almacen = almacen;
		this.lotes = lotes;
		this.controles = controles;
	}

	@Override
	protected void validar(AlmacenProductoTerminado registro) {
		registro.setUbicacionFisica(Validar.normalizar(registro.getUbicacionFisica()));

		Validar.obligatorio(registro.getIdLote(), "lote");
		Validar.mayorQueCero(registro.getCantidad(), "cantidad");
		Validar.obligatorio(registro.getUbicacionFisica(), "ubicacion fisica");
		Validar.obligatorio(registro.getFechaIngreso(), "fecha de ingreso");

		LoteProduccion lote = lotes.buscarPorId(registro.getIdLote())
				.orElseThrow(() -> new ReglaNegocioException("El lote indicado no existe"));

		ControlCalidad control = controles.buscarPorLote(registro.getIdLote())
				.orElseThrow(() -> new ReglaNegocioException("El lote " + lote.getCodigoLote()
						+ " todavia no tiene control de calidad: no puede entrar al almacen"));

		if (!ServicioControlCalidad.APROBADO.equals(control.getResultado())) {
			throw new ReglaNegocioException("El lote " + lote.getCodigoLote() + " esta "
					+ control.getResultado() + ": solo entran al almacen los lotes aprobados");
		}

		if (registro.getCantidad() > lote.getCantidadProducida()) {
			throw new ReglaNegocioException("No se pueden guardar " + registro.getCantidad()
					+ " unidades: el lote " + lote.getCodigoLote() + " produjo "
					+ lote.getCantidadProducida());
		}

		almacen.buscarPorLote(registro.getIdLote())
				.filter(otro -> !otro.getIdAlmacen().equals(registro.getIdAlmacen()))
				.ifPresent(otro -> {
					throw new ReglaNegocioException("Ese lote ya esta guardado en el almacen");
				});
	}
}
