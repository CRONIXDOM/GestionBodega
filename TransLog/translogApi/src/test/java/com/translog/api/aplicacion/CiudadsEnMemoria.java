package com.translog.api.aplicacion;

import com.translog.api.dominio.entidades.Ciudad;
import com.translog.api.dominio.repositorio.ICiudadRepositorio;

public class CiudadsEnMemoria extends RepositorioEnMemoria<Ciudad> implements ICiudadRepositorio {

	public CiudadsEnMemoria() {
		super(Ciudad::getIdCiudad, Ciudad::setIdCiudad);
	}

	@Override
	protected Ciudad copiar(Ciudad c) {
		return new Ciudad(c.getIdCiudad(), c.getNombre());
	}
}
