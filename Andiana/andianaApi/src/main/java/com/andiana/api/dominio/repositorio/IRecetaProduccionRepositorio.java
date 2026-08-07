package com.andiana.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.RecetaProduccion;

/**
 * Lo que el dominio necesita del almacen de datos. Es una interfaz propia, sin
 * nada de JPA ni de Spring: quien la implementa vive en infraestructura.
 */
public interface IRecetaProduccionRepositorio {

	RecetaProduccion guardar(RecetaProduccion nuevoRecetaProduccion);

	Optional<RecetaProduccion> buscarPorid(int idRecetaProduccion);

	List<RecetaProduccion> listarTodos();

	void eliminar(int idRecetaProduccion);

	/** Las versiones de la formula de un producto. */
	List<RecetaProduccion> buscarPorProducto(int idProducto);
}
