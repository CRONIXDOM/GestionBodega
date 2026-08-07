package com.andiana.api.dominio.puerto;

import java.util.Optional;

import com.andiana.api.dominio.modelo.AlmacenProductoTerminado;

public interface AlmacenProductoTerminadoRepositorio extends Repositorio<AlmacenProductoTerminado> {

	/** Lo guardado en almacen para un lote, si ya entro. */
	Optional<AlmacenProductoTerminado> buscarPorLote(Integer idLote);
}
