package com.bodega.control.aplicacion.casosuso.entrada;

import java.util.List;

import com.bodega.control.dominio.entidades.Tipo;

public interface ITipoUseCase {
	
	Tipo guardar (Tipo nuevaTipo);
	
	Tipo buscarPorid (int Tipo);
	
	List<Tipo> listarTodos();
	
	void eliminar (int Tipo);

	Tipo buscarPorId(int idTipo);

}
