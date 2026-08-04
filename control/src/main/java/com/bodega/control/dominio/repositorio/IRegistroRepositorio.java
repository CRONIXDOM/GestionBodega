package com.bodega.control.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Registro;

public interface IRegistroRepositorio {
	
	Registro guardar (Registro nuevaRegistro);
	
	Optional<Registro> buscarPorid (int Registro);
	
	List<Registro> listarTodos();
	
	void eliminar (int Registro);
	

}
