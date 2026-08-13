package com.andiana.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.Producto;

public interface IProductoRepositorio {

	Producto guardar(Producto nuevoProducto);

	Optional<Producto> buscarPorId(int idProducto);

	List<Producto> listarTodos();

	void eliminar(int idProducto);
}
