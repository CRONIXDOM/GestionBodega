package com.bodega.control.aplicacion.casosuso.entrada;

import java.util.List;

import com.bodega.control.dominio.entidades.Producto;

public interface IProductoUseCase {
	
	Producto guardar (Producto nuevaProducto);
	
	Producto buscarPorid (int Producto);
	
	List<Producto> listarTodos();
	
	void eliminar (int Producto);

	Producto buscarPorId(int idProducto);
	

}
