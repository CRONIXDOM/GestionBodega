package com.andiana.api.aplicacion;

import java.util.List;

import com.andiana.api.dominio.entidades.DetalleReceta;
import com.andiana.api.dominio.repositorio.IDetalleRecetaRepositorio;

public class DetallesEnMemoria extends RepositorioEnMemoria<DetalleReceta> implements IDetalleRecetaRepositorio {

	public DetallesEnMemoria() {
		super(DetalleReceta::getIdDetalle, DetalleReceta::setIdDetalle);
	}

	@Override
	protected DetalleReceta copiar(DetalleReceta d) {
		return new DetalleReceta(d.getIdDetalle(), d.getIdReceta(), d.getIdMateria(), d.getCantidad(), d.getUnidad());
	}

	@Override
	public List<DetalleReceta> buscarPorReceta(int idReceta) {
		return listarTodos().stream().filter(d -> d.getIdReceta() == idReceta).toList();
	}
}
