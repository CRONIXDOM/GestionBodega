package com.andiana.api.infraestructura.mapeador;

import com.andiana.api.dominio.modelo.LoteProduccion;
import com.andiana.api.infraestructura.entidad.LoteProduccionEntidad;

/** Traduce entre la tabla y el modelo del dominio, en los dos sentidos. */
public final class LoteProduccionMapeador {

	private LoteProduccionMapeador() {
	}

	public static LoteProduccion aDominio(LoteProduccionEntidad entidad) {
		if (entidad == null) {
			return null;
		}
		LoteProduccion modelo = new LoteProduccion();
		modelo.setIdLote(entidad.getIdLote());
		modelo.setCodigoLote(entidad.getCodigoLote());
		modelo.setIdOrden(entidad.getIdOrden());
		modelo.setCantidadProducida(entidad.getCantidadProducida());
		modelo.setFechaFabricacion(entidad.getFechaFabricacion());
		return modelo;
	}

	public static LoteProduccionEntidad aEntidad(LoteProduccion modelo) {
		if (modelo == null) {
			return null;
		}
		LoteProduccionEntidad entidad = new LoteProduccionEntidad();
		entidad.setIdLote(modelo.getIdLote());
		entidad.setCodigoLote(modelo.getCodigoLote());
		entidad.setIdOrden(modelo.getIdOrden());
		entidad.setCantidadProducida(modelo.getCantidadProducida());
		entidad.setFechaFabricacion(modelo.getFechaFabricacion());
		return entidad;
	}
}
