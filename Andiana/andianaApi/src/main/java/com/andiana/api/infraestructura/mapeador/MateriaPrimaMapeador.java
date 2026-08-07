package com.andiana.api.infraestructura.mapeador;

import com.andiana.api.dominio.modelo.MateriaPrima;
import com.andiana.api.infraestructura.entidad.MateriaPrimaEntidad;

/** Traduce entre la tabla y el modelo del dominio, en los dos sentidos. */
public final class MateriaPrimaMapeador {

	private MateriaPrimaMapeador() {
	}

	public static MateriaPrima aDominio(MateriaPrimaEntidad entidad) {
		if (entidad == null) {
			return null;
		}
		MateriaPrima modelo = new MateriaPrima();
		modelo.setIdMateriaPrima(entidad.getIdMateriaPrima());
		modelo.setNombre(entidad.getNombre());
		modelo.setUnidadMedida(entidad.getUnidadMedida());
		modelo.setStock(entidad.getStock());
		return modelo;
	}

	public static MateriaPrimaEntidad aEntidad(MateriaPrima modelo) {
		if (modelo == null) {
			return null;
		}
		MateriaPrimaEntidad entidad = new MateriaPrimaEntidad();
		entidad.setIdMateriaPrima(modelo.getIdMateriaPrima());
		entidad.setNombre(modelo.getNombre());
		entidad.setUnidadMedida(modelo.getUnidadMedida());
		entidad.setStock(modelo.getStock());
		return entidad;
	}
}
