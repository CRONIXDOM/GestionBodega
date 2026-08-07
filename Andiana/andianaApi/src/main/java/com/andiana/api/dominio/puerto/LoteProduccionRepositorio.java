package com.andiana.api.dominio.puerto;

import java.util.List;

import com.andiana.api.dominio.modelo.LoteProduccion;

public interface LoteProduccionRepositorio extends Repositorio<LoteProduccion> {

	/** Los lotes que salieron de una orden. */
	List<LoteProduccion> buscarPorOrden(Integer idOrden);
}
