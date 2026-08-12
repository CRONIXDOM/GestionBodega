package com.bodega.control.aplicacion.casosuso.entrada;

import java.util.List;

import com.bodega.control.dominio.entidades.Producto;

public interface IProductoUseCase {
	
	Producto guardar (Producto nuevaProducto);
	
	Producto buscarPorid (int Producto);
	
	/** Todos, incluidos los dados de baja: sirve para poder nombrarlos en el historial. */
	List<Producto> listarTodos();

	/** Los que se ven en el sistema. */
	List<Producto> listarActivos();

	/** Los dados de baja, para poder recuperarlos. */
	List<Producto> listarEliminados();

	/** Da de baja el producto: sale de los listados pero no se pierde. */
	void eliminar (int Producto);

	/** Devuelve a los listados un producto dado de baja. */
	Producto recuperar (int idProducto);

	Producto buscarPorId(int idProducto);
	

}
