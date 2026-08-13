package com.andiana.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.ControlCalidad;

public interface IControlCalidadRepositorio {

	ControlCalidad guardar(ControlCalidad nuevoControlCalidad);

	Optional<ControlCalidad> buscarPorid(int idControlCalidad);

	List<ControlCalidad> listarTodos();

	void eliminar(int idControlCalidad);

	List<ControlCalidad> buscarPorLote(int idLote);
}
