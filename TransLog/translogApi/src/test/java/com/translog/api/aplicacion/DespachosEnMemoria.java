package com.translog.api.aplicacion;

import com.translog.api.dominio.entidades.Despacho;
import com.translog.api.dominio.repositorio.IDespachoRepositorio;

public class DespachosEnMemoria extends RepositorioEnMemoria<Despacho> implements IDespachoRepositorio {

	public DespachosEnMemoria() {
		super(Despacho::getIdDespacho, Despacho::setIdDespacho);
	}

	@Override
	protected Despacho copiar(Despacho d) {
		return new Despacho(d.getIdDespacho(), d.getFechaDespacho(), d.getIdRuta(), d.getIdVehiculo(), d.getIdConductor(), d.getEstado());
	}
}
