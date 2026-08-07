package com.andiana.api.infraestructura.mapeador;

import com.andiana.api.dominio.modelo.MovimientoInventario;
import com.andiana.api.infraestructura.entidad.MovimientoInventarioEntidad;

/** Traduce entre la tabla y el modelo del dominio, en los dos sentidos. */
public final class MovimientoInventarioMapeador {

	private MovimientoInventarioMapeador() {
	}

	public static MovimientoInventario aDominio(MovimientoInventarioEntidad entidad) {
		if (entidad == null) {
			return null;
		}
		MovimientoInventario modelo = new MovimientoInventario();
		modelo.setIdMovimiento(entidad.getIdMovimiento());
		modelo.setIdMateriaPrima(entidad.getIdMateriaPrima());
		modelo.setTipo(entidad.getTipo());
		modelo.setCantidad(entidad.getCantidad());
		modelo.setFecha(entidad.getFecha());
		modelo.setObservacion(entidad.getObservacion());
		return modelo;
	}

	public static MovimientoInventarioEntidad aEntidad(MovimientoInventario modelo) {
		if (modelo == null) {
			return null;
		}
		MovimientoInventarioEntidad entidad = new MovimientoInventarioEntidad();
		entidad.setIdMovimiento(modelo.getIdMovimiento());
		entidad.setIdMateriaPrima(modelo.getIdMateriaPrima());
		entidad.setTipo(modelo.getTipo());
		entidad.setCantidad(modelo.getCantidad());
		entidad.setFecha(modelo.getFecha());
		entidad.setObservacion(modelo.getObservacion());
		return entidad;
	}
}
