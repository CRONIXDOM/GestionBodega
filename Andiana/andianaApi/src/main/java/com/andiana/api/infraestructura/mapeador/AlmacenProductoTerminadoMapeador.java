package com.andiana.api.infraestructura.mapeador;

import com.andiana.api.dominio.modelo.AlmacenProductoTerminado;
import com.andiana.api.infraestructura.entidad.AlmacenProductoTerminadoEntidad;

/** Traduce entre la tabla y el modelo del dominio, en los dos sentidos. */
public final class AlmacenProductoTerminadoMapeador {

	private AlmacenProductoTerminadoMapeador() {
	}

	public static AlmacenProductoTerminado aDominio(AlmacenProductoTerminadoEntidad entidad) {
		if (entidad == null) {
			return null;
		}
		AlmacenProductoTerminado modelo = new AlmacenProductoTerminado();
		modelo.setIdAlmacen(entidad.getIdAlmacen());
		modelo.setIdLote(entidad.getIdLote());
		modelo.setCantidad(entidad.getCantidad());
		modelo.setUbicacionFisica(entidad.getUbicacionFisica());
		modelo.setFechaIngreso(entidad.getFechaIngreso());
		return modelo;
	}

	public static AlmacenProductoTerminadoEntidad aEntidad(AlmacenProductoTerminado modelo) {
		if (modelo == null) {
			return null;
		}
		AlmacenProductoTerminadoEntidad entidad = new AlmacenProductoTerminadoEntidad();
		entidad.setIdAlmacen(modelo.getIdAlmacen());
		entidad.setIdLote(modelo.getIdLote());
		entidad.setCantidad(modelo.getCantidad());
		entidad.setUbicacionFisica(modelo.getUbicacionFisica());
		entidad.setFechaIngreso(modelo.getFechaIngreso());
		return entidad;
	}
}
