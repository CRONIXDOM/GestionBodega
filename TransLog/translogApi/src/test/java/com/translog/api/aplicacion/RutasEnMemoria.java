package com.translog.api.aplicacion;

import com.translog.api.dominio.entidades.Ruta;
import com.translog.api.dominio.repositorio.IRutaRepositorio;

public class RutasEnMemoria extends RepositorioEnMemoria<Ruta> implements IRutaRepositorio {

	public RutasEnMemoria() {
		super(Ruta::getIdRuta, Ruta::setIdRuta);
	}

	@Override
	protected Ruta copiar(Ruta r) {
		return new Ruta(r.getIdRuta(), r.getIdCiudadOrigen(), r.getIdCiudadDestino(), r.getDistanciaKm());
	}
}
