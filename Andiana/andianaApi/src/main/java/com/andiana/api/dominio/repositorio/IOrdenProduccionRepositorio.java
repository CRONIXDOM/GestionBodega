package com.andiana.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.OrdenProduccion;

/**
 * Lo que el dominio necesita del almacen de datos. Es una interfaz propia, sin
 * nada de JPA ni de Spring: quien la implementa vive en infraestructura.
 */
public interface IOrdenProduccionRepositorio {

	OrdenProduccion guardar(OrdenProduccion nuevoOrdenProduccion);

	Optional<OrdenProduccion> buscarPorid(int idOrdenProduccion);

	List<OrdenProduccion> listarTodos();

	void eliminar(int idOrdenProduccion);
}
