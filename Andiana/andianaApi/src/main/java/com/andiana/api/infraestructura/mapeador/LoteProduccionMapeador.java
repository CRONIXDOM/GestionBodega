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
		modelo.setIdOrden(entidad.getIdOrden());
		modelo.setNumeroLote(entidad.getNumeroLote());
		modelo.setFechaInicio(entidad.getFechaInicio());
		modelo.setFechaFin(entidad.getFechaFin());
		modelo.setCantidadProducida(entidad.getCantidadProducida());
		modelo.setEstado(entidad.getEstado());
		return modelo;
	}

	public static LoteProduccionEntidad aEntidad(LoteProduccion modelo) {
		if (modelo == null) {
			return null;
		}
		LoteProduccionEntidad entidad = new LoteProduccionEntidad();
		entidad.setIdLote(modelo.getIdLote());
		entidad.setIdOrden(modelo.getIdOrden());
		entidad.setNumeroLote(modelo.getNumeroLote());
		entidad.setFechaInicio(modelo.getFechaInicio());
		entidad.setFechaFin(modelo.getFechaFin());
		entidad.setCantidadProducida(modelo.getCantidadProducida());
		entidad.setEstado(modelo.getEstado());
		return entidad;
	}
}
