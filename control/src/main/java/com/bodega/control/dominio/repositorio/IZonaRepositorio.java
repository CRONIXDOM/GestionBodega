package com.bodega.control.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Zona;

public interface IZonaRepositorio {
	
	Zona guardar (Zona nuevaZona);
	
	Optional<Zona> buscarPorid (int Zona);
	
	List<Zona> listarTodos();
	
	void eliminar (int Zona);

}
