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
		modelo.setIdMateria(entidad.getIdMateria());
		modelo.setNombre(entidad.getNombre());
		modelo.setUnidadMedida(entidad.getUnidadMedida());
		modelo.setStockActual(entidad.getStockActual());
		modelo.setStockMinimo(entidad.getStockMinimo());
		return modelo;
	}

	public static MateriaPrimaEntidad aEntidad(MateriaPrima modelo) {
		if (modelo == null) {
			return null;
		}
		MateriaPrimaEntidad entidad = new MateriaPrimaEntidad();
		entidad.setIdMateria(modelo.getIdMateria());
		entidad.setNombre(modelo.getNombre());
		entidad.setUnidadMedida(modelo.getUnidadMedida());
		entidad.setStockActual(modelo.getStockActual());
		entidad.setStockMinimo(modelo.getStockMinimo());
		return entidad;
	}
}
