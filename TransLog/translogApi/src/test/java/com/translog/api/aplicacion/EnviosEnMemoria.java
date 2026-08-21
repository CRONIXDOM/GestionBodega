package com.translog.api.aplicacion;

import com.translog.api.dominio.entidades.Envio;
import com.translog.api.dominio.repositorio.IEnvioRepositorio;

public class EnviosEnMemoria extends RepositorioEnMemoria<Envio> implements IEnvioRepositorio {

	public EnviosEnMemoria() {
		super(Envio::getIdEnvio, Envio::setIdEnvio);
	}

	@Override
	protected Envio copiar(Envio e) {
		return new Envio(e.getIdEnvio(), e.getIdCiudadOrigen(), e.getIdCiudadDestino(), e.getPeso(), e.getFechaRegistro(), e.getValorDeclarado(), e.getEstado(), e.getIdDespacho());
	}
}
