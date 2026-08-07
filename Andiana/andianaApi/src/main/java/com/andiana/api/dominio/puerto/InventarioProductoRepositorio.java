package com.andiana.api.dominio.puerto;

import java.util.List;

import com.andiana.api.dominio.modelo.InventarioProducto;

public interface InventarioProductoRepositorio extends Repositorio<InventarioProducto> {

	/** Lo que hay guardado en almacen de un lote (puede estar en varios sitios). */
	List<InventarioProducto> buscarPorLote(Integer idLote);
}
