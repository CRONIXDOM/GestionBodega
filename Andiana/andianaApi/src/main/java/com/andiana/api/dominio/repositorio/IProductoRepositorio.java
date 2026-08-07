package com.andiana.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.Producto;

/**
 * Lo que el dominio necesita del almacen de datos. Es una interfaz propia, sin
 * nada de JPA ni de Spring: quien la implementa vive en infraestructura.
 */
public interface IProductoRepositorio {

	Producto guardar(Producto nuevoProducto);

	Optional<Producto> buscarPorid(int idProducto);

	List<Producto> listarTodos();

	void eliminar(int idProducto);
}
