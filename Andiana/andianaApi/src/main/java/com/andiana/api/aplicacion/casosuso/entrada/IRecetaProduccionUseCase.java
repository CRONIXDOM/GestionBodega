package com.andiana.api.aplicacion.casosuso.entrada;

import java.util.List;

import com.andiana.api.dominio.entidades.RecetaProduccion;

public interface IRecetaProduccionUseCase {

	RecetaProduccion guardar(RecetaProduccion nuevoRecetaProduccion);

	RecetaProduccion buscarPorId(int idReceta);

	List<RecetaProduccion> listarTodos();

	void eliminar(int idReceta);

}
