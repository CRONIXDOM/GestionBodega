package com.bodega.control.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Sede;

public interface ISedeRepositorio {

	Sede guardar(Sede nuevaSede);

	Optional<Sede> buscarPorid(int idSede);

	List<Sede> listarTodos();

	void eliminar(int idSede);

}
