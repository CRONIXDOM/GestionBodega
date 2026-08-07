package com.andiana.api.aplicacion.casosuso.entrada;

import java.util.List;

import com.andiana.api.dominio.entidades.Producto;

public interface IProductoUseCase {

	Producto guardar(Producto nuevoProducto);

	Producto buscarPorId(int idProducto);

	List<Producto> listarTodos();

	void eliminar(int idProducto);

}
