package com.andiana.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.DetalleReceta;


public interface IDetalleRecetaRepositorio {

	DetalleReceta guardar(DetalleReceta nuevoDetalleReceta);

	Optional<DetalleReceta> buscarPorId(int idDetalleReceta);

	List<DetalleReceta> listarTodos();

	void eliminar(int idDetalleReceta);

	List<DetalleReceta> buscarPorReceta(int idReceta);
}
