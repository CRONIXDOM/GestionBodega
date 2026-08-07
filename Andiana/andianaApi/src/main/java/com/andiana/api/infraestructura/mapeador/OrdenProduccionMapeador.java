package com.andiana.api.infraestructura.mapeador;

import com.andiana.api.dominio.modelo.OrdenProduccion;
import com.andiana.api.infraestructura.entidad.OrdenProduccionEntidad;

/** Traduce entre la tabla y el modelo del dominio, en los dos sentidos. */
public final class OrdenProduccionMapeador {

	private OrdenProduccionMapeador() {
	}

	public static OrdenProduccion aDominio(OrdenProduccionEntidad entidad) {
		if (entidad == null) {
			return null;
		}
		OrdenProduccion modelo = new OrdenProduccion();
		modelo.setIdOrden(entidad.getIdOrden());
		modelo.setIdProducto(entidad.getIdProducto());
		modelo.setFechaProgramada(entidad.getFechaProgramada());
		modelo.setCantidadProgramada(entidad.getCantidadProgramada());
		modelo.setEstado(entidad.getEstado());
		modelo.setResponsable(entidad.getResponsable());
		return modelo;
	}

	public static OrdenProduccionEntidad aEntidad(OrdenProduccion modelo) {
		if (modelo == null) {
			return null;
		}
		OrdenProduccionEntidad entidad = new OrdenProduccionEntidad();
		entidad.setIdOrden(modelo.getIdOrden());
		entidad.setIdProducto(modelo.getIdProducto());
		entidad.setFechaProgramada(modelo.getFechaProgramada());
		entidad.setCantidadProgramada(modelo.getCantidadProgramada());
		entidad.setEstado(modelo.getEstado());
		entidad.setResponsable(modelo.getResponsable());
		return entidad;
	}
}
