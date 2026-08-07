package com.andiana.api.infraestructura.mapeador;

import com.andiana.api.dominio.modelo.Receta;
import com.andiana.api.infraestructura.entidad.RecetaEntidad;

/** Traduce entre la tabla y el modelo del dominio, en los dos sentidos. */
public final class RecetaMapeador {

	private RecetaMapeador() {
	}

	public static Receta aDominio(RecetaEntidad entidad) {
		if (entidad == null) {
			return null;
		}
		Receta modelo = new Receta();
		modelo.setIdReceta(entidad.getIdReceta());
		modelo.setIdProducto(entidad.getIdProducto());
		modelo.setVersion(entidad.getVersion());
		modelo.setFecha(entidad.getFecha());
		modelo.setActiva(entidad.getActiva());
		return modelo;
	}

	public static RecetaEntidad aEntidad(Receta modelo) {
		if (modelo == null) {
			return null;
		}
		RecetaEntidad entidad = new RecetaEntidad();
		entidad.setIdReceta(modelo.getIdReceta());
		entidad.setIdProducto(modelo.getIdProducto());
		entidad.setVersion(modelo.getVersion());
		entidad.setFecha(modelo.getFecha());
		entidad.setActiva(modelo.getActiva());
		return entidad;
	}
}
