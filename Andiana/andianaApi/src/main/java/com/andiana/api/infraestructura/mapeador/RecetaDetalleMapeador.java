package com.andiana.api.infraestructura.mapeador;

import com.andiana.api.dominio.modelo.RecetaDetalle;
import com.andiana.api.infraestructura.entidad.RecetaDetalleEntidad;

/** Traduce entre la tabla y el modelo del dominio, en los dos sentidos. */
public final class RecetaDetalleMapeador {

	private RecetaDetalleMapeador() {
	}

	public static RecetaDetalle aDominio(RecetaDetalleEntidad entidad) {
		if (entidad == null) {
			return null;
		}
		RecetaDetalle modelo = new RecetaDetalle();
		modelo.setIdRecetaDetalle(entidad.getIdRecetaDetalle());
		modelo.setIdReceta(entidad.getIdReceta());
		modelo.setIdMateriaPrima(entidad.getIdMateriaPrima());
		modelo.setCantidad(entidad.getCantidad());
		return modelo;
	}

	public static RecetaDetalleEntidad aEntidad(RecetaDetalle modelo) {
		if (modelo == null) {
			return null;
		}
		RecetaDetalleEntidad entidad = new RecetaDetalleEntidad();
		entidad.setIdRecetaDetalle(modelo.getIdRecetaDetalle());
		entidad.setIdReceta(modelo.getIdReceta());
		entidad.setIdMateriaPrima(modelo.getIdMateriaPrima());
		entidad.setCantidad(modelo.getCantidad());
		return entidad;
	}
}
