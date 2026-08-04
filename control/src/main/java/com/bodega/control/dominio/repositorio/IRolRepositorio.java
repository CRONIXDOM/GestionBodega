package com.bodega.control.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Rol;

public interface IRolRepositorio {
	
	Rol guardar (Rol nuevaRol);
	
	Optional<Rol> buscarPorid (int Rol);
	
	List<Rol> listarTodos();
	
	void eliminar (int Rol);

}
