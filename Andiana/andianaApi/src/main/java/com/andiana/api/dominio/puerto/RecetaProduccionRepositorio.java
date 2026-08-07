package com.andiana.api.dominio.puerto;

import java.util.List;

import com.andiana.api.dominio.modelo.RecetaProduccion;

public interface RecetaProduccionRepositorio extends Repositorio<RecetaProduccion> {

	/** Las versiones de la formula de un producto. */
	List<RecetaProduccion> buscarPorProducto(Integer idProducto);
}
