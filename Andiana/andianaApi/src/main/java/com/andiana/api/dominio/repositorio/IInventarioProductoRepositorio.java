package com.andiana.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.InventarioProducto;

/**
 * Lo que el dominio necesita del almacen de datos. Es una interfaz propia, sin
 * nada de JPA ni de Spring: quien la implementa vive en infraestructura.
 */
public interface IInventarioProductoRepositorio {

	InventarioProducto guardar(InventarioProducto nuevoInventarioProducto);

	Optional<InventarioProducto> buscarPorid(int idInventarioProducto);

	List<InventarioProducto> listarTodos();

	void eliminar(int idInventarioProducto);

	/** Lo guardado de un lote, que puede estar en varias ubicaciones. */
	List<InventarioProducto> buscarPorLote(int idLote);
}
