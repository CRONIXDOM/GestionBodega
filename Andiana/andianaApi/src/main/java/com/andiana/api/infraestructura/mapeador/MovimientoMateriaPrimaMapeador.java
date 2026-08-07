package com.andiana.api.infraestructura.mapeador;

import com.andiana.api.dominio.modelo.MovimientoMateriaPrima;
import com.andiana.api.infraestructura.entidad.MovimientoMateriaPrimaEntidad;

/** Traduce entre la tabla y el modelo del dominio, en los dos sentidos. */
public final class MovimientoMateriaPrimaMapeador {

	private MovimientoMateriaPrimaMapeador() {
	}

	public static MovimientoMateriaPrima aDominio(MovimientoMateriaPrimaEntidad entidad) {
		if (entidad == null) {
			return null;
		}
		MovimientoMateriaPrima modelo = new MovimientoMateriaPrima();
		modelo.setIdMovimiento(entidad.getIdMovimiento());
		modelo.setIdMateria(entidad.getIdMateria());
		modelo.setFecha(entidad.getFecha());
		modelo.setTipo(entidad.getTipo());
		modelo.setCantidad(entidad.getCantidad());
		modelo.setObservacion(entidad.getObservacion());
		return modelo;
	}

	public static MovimientoMateriaPrimaEntidad aEntidad(MovimientoMateriaPrima modelo) {
		if (modelo == null) {
			return null;
		}
		MovimientoMateriaPrimaEntidad entidad = new MovimientoMateriaPrimaEntidad();
		entidad.setIdMovimiento(modelo.getIdMovimiento());
		entidad.setIdMateria(modelo.getIdMateria());
		entidad.setFecha(modelo.getFecha());
		entidad.setTipo(modelo.getTipo());
		entidad.setCantidad(modelo.getCantidad());
		entidad.setObservacion(modelo.getObservacion());
		return entidad;
	}
}
