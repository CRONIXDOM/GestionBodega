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
		modelo.setCodigo(entidad.getCodigo());
		modelo.setIdProducto(entidad.getIdProducto());
		modelo.setCantidadProgramada(entidad.getCantidadProgramada());
		modelo.setFechaProduccion(entidad.getFechaProduccion());
		modelo.setEstado(entidad.getEstado());
		return modelo;
	}

	public static OrdenProduccionEntidad aEntidad(OrdenProduccion modelo) {
		if (modelo == null) {
			return null;
		}
		OrdenProduccionEntidad entidad = new OrdenProduccionEntidad();
		entidad.setIdOrden(modelo.getIdOrden());
		entidad.setCodigo(modelo.getCodigo());
		entidad.setIdProducto(modelo.getIdProducto());
		entidad.setCantidadProgramada(modelo.getCantidadProgramada());
		entidad.setFechaProduccion(modelo.getFechaProduccion());
		entidad.setEstado(modelo.getEstado());
		return entidad;
	}
}
