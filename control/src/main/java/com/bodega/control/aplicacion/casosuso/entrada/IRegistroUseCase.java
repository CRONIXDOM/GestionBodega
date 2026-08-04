package com.bodega.control.aplicacion.casosuso.entrada;

import java.util.List;

import com.bodega.control.dominio.entidades.Registro;

public interface IRegistroUseCase {
	
	Registro guardar (Registro nuevaRegistro);
	
	Registro buscarPorid (int Registro);
	
	List<Registro> listarTodos();
	
	void eliminar (int Registro);

	Registro buscarPorId(int idRegistro);

}
