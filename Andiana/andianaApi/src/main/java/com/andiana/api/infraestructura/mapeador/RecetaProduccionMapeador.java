package com.andiana.api.infraestructura.mapeador;

import com.andiana.api.dominio.modelo.RecetaProduccion;
import com.andiana.api.infraestructura.entidad.RecetaProduccionEntidad;

/** Traduce entre la tabla y el modelo del dominio, en los dos sentidos. */
public final class RecetaProduccionMapeador {

	private RecetaProduccionMapeador() {
	}

	public static RecetaProduccion aDominio(RecetaProduccionEntidad entidad) {
		if (entidad == null) {
			return null;
		}
		RecetaProduccion modelo = new RecetaProduccion();
		modelo.setIdReceta(entidad.getIdReceta());
		modelo.setIdProducto(entidad.getIdProducto());
		modelo.setVersion(entidad.getVersion());
		modelo.setFechaVigencia(entidad.getFechaVigencia());
		modelo.setEstado(entidad.getEstado());
		return modelo;
	}

	public static RecetaProduccionEntidad aEntidad(RecetaProduccion modelo) {
		if (modelo == null) {
			return null;
		}
		RecetaProduccionEntidad entidad = new RecetaProduccionEntidad();
		entidad.setIdReceta(modelo.getIdReceta());
		entidad.setIdProducto(modelo.getIdProducto());
		entidad.setVersion(modelo.getVersion());
		entidad.setFechaVigencia(modelo.getFechaVigencia());
		entidad.setEstado(modelo.getEstado());
		return entidad;
	}
}
