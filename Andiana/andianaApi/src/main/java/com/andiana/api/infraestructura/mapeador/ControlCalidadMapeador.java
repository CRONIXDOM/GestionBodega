package com.andiana.api.infraestructura.mapeador;

import com.andiana.api.dominio.modelo.ControlCalidad;
import com.andiana.api.infraestructura.entidad.ControlCalidadEntidad;

/** Traduce entre la tabla y el modelo del dominio, en los dos sentidos. */
public final class ControlCalidadMapeador {

	private ControlCalidadMapeador() {
	}

	public static ControlCalidad aDominio(ControlCalidadEntidad entidad) {
		if (entidad == null) {
			return null;
		}
		ControlCalidad modelo = new ControlCalidad();
		modelo.setIdControl(entidad.getIdControl());
		modelo.setIdLote(entidad.getIdLote());
		modelo.setPh(entidad.getPh());
		modelo.setGradosBrix(entidad.getGradosBrix());
		modelo.setTemperatura(entidad.getTemperatura());
		modelo.setResultado(entidad.getResultado());
		modelo.setFechaInspeccion(entidad.getFechaInspeccion());
		return modelo;
	}

	public static ControlCalidadEntidad aEntidad(ControlCalidad modelo) {
		if (modelo == null) {
			return null;
		}
		ControlCalidadEntidad entidad = new ControlCalidadEntidad();
		entidad.setIdControl(modelo.getIdControl());
		entidad.setIdLote(modelo.getIdLote());
		entidad.setPh(modelo.getPh());
		entidad.setGradosBrix(modelo.getGradosBrix());
		entidad.setTemperatura(modelo.getTemperatura());
		entidad.setResultado(modelo.getResultado());
		entidad.setFechaInspeccion(modelo.getFechaInspeccion());
		return entidad;
	}
}
