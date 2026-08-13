package com.andiana.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.RecetaProduccion;


public interface IRecetaProduccionRepositorio {

	RecetaProduccion guardar(RecetaProduccion nuevoRecetaProduccion);

	Optional<RecetaProduccion> buscarPorId(int idRecetaProduccion);

	List<RecetaProduccion> listarTodos();

	void eliminar(int idRecetaProduccion);

	List<RecetaProduccion> buscarPorProducto(int idProducto);
}
