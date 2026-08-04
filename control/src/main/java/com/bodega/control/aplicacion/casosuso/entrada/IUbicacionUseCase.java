package com.bodega.control.aplicacion.casosuso.entrada;

import java.util.List;

import com.bodega.control.dominio.entidades.Ubicacion;

public interface IUbicacionUseCase {
	
	Ubicacion guardar (Ubicacion nuevaUbicacion);
	
	Ubicacion buscarPorid (int Ubicacion);
	
	List<Ubicacion> listarTodos();
	
	void eliminar (int Ubicacion);

	Ubicacion buscarPorId(int idUbicacion);

}
