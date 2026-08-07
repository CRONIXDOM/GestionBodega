package com.andiana.api.aplicacion.casosuso.entrada;

import java.util.List;

import com.andiana.api.dominio.entidades.InventarioProducto;

public interface IInventarioProductoUseCase {

	InventarioProducto guardar(InventarioProducto nuevoInventarioProducto);

	InventarioProducto buscarPorId(int idInventario);

	List<InventarioProducto> listarTodos();

	void eliminar(int idInventario);

}
