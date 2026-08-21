package com.translog.api.aplicacion;

import com.translog.api.dominio.entidades.Conductor;
import com.translog.api.dominio.repositorio.IConductorRepositorio;

public class ConductorsEnMemoria extends RepositorioEnMemoria<Conductor> implements IConductorRepositorio {

	public ConductorsEnMemoria() {
		super(Conductor::getIdConductor, Conductor::setIdConductor);
	}

	@Override
	protected Conductor copiar(Conductor c) {
		return new Conductor(c.getIdConductor(), c.getNombre(), c.getLicencia(), c.getEstado());
	}
}
