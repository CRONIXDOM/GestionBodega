package com.andiana.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.InventarioProducto;

public interface IInventarioProductoRepositorio {

	InventarioProducto guardar(InventarioProducto nuevoInventarioProducto);

	Optional<InventarioProducto> buscarPorid(int idInventarioProducto);

	List<InventarioProducto> listarTodos();

	void eliminar(int idInventarioProducto);

	List<InventarioProducto> buscarPorLote(int idLote);
}
