package com.translog.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.translog.api.dominio.entidades.Ciudad;

public interface ICiudadRepositorio {

	Ciudad guardar(Ciudad nuevoCiudad);

	Optional<Ciudad> buscarPorId(int idCiudad);

	List<Ciudad> listarTodos();

	void eliminar(int idCiudad);

}
