package com.bodega.control.aplicacion.casosuso.entrada;

import java.util.List;

import com.bodega.control.dominio.entidades.Zona;

public interface IZonaUseCase {
	
	Zona guardar (Zona nuevaZona);
	
	Zona buscarPorid (int Zona);
	
	List<Zona> listarTodo();
	
	void eliminar (int Zona);

	Zona buscarPorId(int idZona);

	List<Zona> listarTodos();
}
