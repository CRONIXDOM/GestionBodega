package com.andiana.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.DetalleReceta;

/**
 * Lo que el dominio necesita del almacen de datos. Es una interfaz propia, sin
 * nada de JPA ni de Spring: quien la implementa vive en infraestructura.
 */
public interface IDetalleRecetaRepositorio {

	DetalleReceta guardar(DetalleReceta nuevoDetalleReceta);

	Optional<DetalleReceta> buscarPorid(int idDetalleReceta);

	List<DetalleReceta> listarTodos();

	void eliminar(int idDetalleReceta);

	/** Las materias primas que lleva una receta. */
	List<DetalleReceta> buscarPorReceta(int idReceta);
}
