package com.andiana.api.dominio.puerto;

import java.util.List;

import com.andiana.api.dominio.modelo.RecetaDetalle;

public interface RecetaDetalleRepositorio extends Repositorio<RecetaDetalle> {

	/** Las materias primas que lleva una receta. */
	List<RecetaDetalle> buscarPorReceta(Integer idReceta);
}
