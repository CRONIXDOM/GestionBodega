package com.bodega.control.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Ubicacion;

public interface IUbicacionRepositorio {
	
	Ubicacion guardar (Ubicacion nuevaUbicacion);
	
	Optional<Ubicacion> buscarPorid (int Ubicacion);
	
	List<Ubicacion> listarTodos();
	
	void eliminar (int Ubicacion);

}
