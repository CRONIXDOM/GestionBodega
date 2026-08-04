package com.bodega.control.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Producto;

public interface IProductoRepositorio {
	
	Producto guardar (Producto nuevaProducto);
	
	Optional<Producto> buscarPorid (int Producto);
	
	List<Producto> listarTodos();
	
	void eliminar (int Producto);

}
