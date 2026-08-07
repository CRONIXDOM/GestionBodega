package com.andiana.api.dominio.puerto;

import java.util.List;

import com.andiana.api.dominio.modelo.DetalleReceta;

public interface DetalleRecetaRepositorio extends Repositorio<DetalleReceta> {

	/** Las materias primas que lleva una receta. */
	List<DetalleReceta> buscarPorReceta(Integer idReceta);
}
