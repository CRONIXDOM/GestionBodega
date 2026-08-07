package com.andiana.api.dominio.puerto;

import java.util.List;

import com.andiana.api.dominio.modelo.Receta;

public interface RecetaRepositorio extends Repositorio<Receta> {

	/** Las recetas de un producto, para ver como fue cambiando la formula. */
	List<Receta> buscarPorProducto(Integer idProducto);
}
