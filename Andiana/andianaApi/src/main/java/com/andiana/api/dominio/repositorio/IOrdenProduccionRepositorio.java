package com.andiana.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.OrdenProduccion;


public interface IOrdenProduccionRepositorio {

	OrdenProduccion guardar(OrdenProduccion nuevoOrdenProduccion);

	Optional<OrdenProduccion> buscarPorId(int idOrdenProduccion);

	List<OrdenProduccion> listarTodos();

	void eliminar(int idOrdenProduccion);
}
