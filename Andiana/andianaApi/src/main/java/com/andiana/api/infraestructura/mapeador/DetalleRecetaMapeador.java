package com.andiana.api.infraestructura.mapeador;

import com.andiana.api.dominio.modelo.DetalleReceta;
import com.andiana.api.infraestructura.entidad.DetalleRecetaEntidad;

/** Traduce entre la tabla y el modelo del dominio, en los dos sentidos. */
public final class DetalleRecetaMapeador {

	private DetalleRecetaMapeador() {
	}

	public static DetalleReceta aDominio(DetalleRecetaEntidad entidad) {
		if (entidad == null) {
			return null;
		}
		DetalleReceta modelo = new DetalleReceta();
		modelo.setIdDetalle(entidad.getIdDetalle());
		modelo.setIdReceta(entidad.getIdReceta());
		modelo.setIdMateria(entidad.getIdMateria());
		modelo.setCantidad(entidad.getCantidad());
		modelo.setUnidad(entidad.getUnidad());
		return modelo;
	}

	public static DetalleRecetaEntidad aEntidad(DetalleReceta modelo) {
		if (modelo == null) {
			return null;
		}
		DetalleRecetaEntidad entidad = new DetalleRecetaEntidad();
		entidad.setIdDetalle(modelo.getIdDetalle());
		entidad.setIdReceta(modelo.getIdReceta());
		entidad.setIdMateria(modelo.getIdMateria());
		entidad.setCantidad(modelo.getCantidad());
		entidad.setUnidad(modelo.getUnidad());
		return entidad;
	}
}
