package com.andiana.api.aplicacion;

import java.util.List;

import com.andiana.api.dominio.entidades.RecetaProduccion;
import com.andiana.api.dominio.repositorio.IRecetaProduccionRepositorio;

public class RecetasEnMemoria extends RepositorioEnMemoria<RecetaProduccion>
		implements IRecetaProduccionRepositorio {

	public RecetasEnMemoria() {
		super(RecetaProduccion::getIdReceta, RecetaProduccion::setIdReceta);
	}

	@Override
	protected RecetaProduccion copiar(RecetaProduccion r) {
		return new RecetaProduccion(r.getIdReceta(), r.getIdProducto(), r.getVersion(), r.getFechaVigencia(),
				r.getEstado());
	}

	@Override
	public List<RecetaProduccion> buscarPorProducto(int idProducto) {
		return listarTodos().stream().filter(r -> r.getIdProducto() == idProducto).toList();
	}
}
