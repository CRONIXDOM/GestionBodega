package com.andiana.api.dominio.puerto;

import java.util.Optional;

import com.andiana.api.dominio.modelo.ControlCalidad;

public interface ControlCalidadRepositorio extends Repositorio<ControlCalidad> {

	/** El control del laboratorio para un lote, si ya se hizo. */
	Optional<ControlCalidad> buscarPorLote(Integer idLote);
}
