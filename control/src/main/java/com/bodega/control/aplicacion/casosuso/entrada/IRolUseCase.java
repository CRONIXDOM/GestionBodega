package com.bodega.control.aplicacion.casosuso.entrada;

import java.util.List;

import com.bodega.control.dominio.entidades.Rol;

public interface IRolUseCase {
	
	Rol guardar (Rol nuevaRol);
	
	Rol buscarPorid (int Rol);
	
	List<Rol> listarTodos();
	
	void eliminar (int Rol);

	Rol buscarPorId(int idRol);
}
