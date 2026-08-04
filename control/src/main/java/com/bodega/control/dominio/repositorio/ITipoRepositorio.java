package com.bodega.control.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Tipo;

public interface ITipoRepositorio {
	
	Tipo guardar (Tipo nuevaTipo);
	
	Optional<Tipo> buscarPorid (int Tipo);
	
	List<Tipo> listarTodos();
	
	void eliminar (int Tipo);

}
