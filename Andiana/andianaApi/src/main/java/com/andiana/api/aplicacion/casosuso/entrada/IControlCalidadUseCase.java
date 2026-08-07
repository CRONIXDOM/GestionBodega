package com.andiana.api.aplicacion.casosuso.entrada;

import java.util.List;

import com.andiana.api.dominio.entidades.ControlCalidad;

public interface IControlCalidadUseCase {

	ControlCalidad guardar(ControlCalidad nuevoControlCalidad);

	ControlCalidad buscarPorId(int idControl);

	List<ControlCalidad> listarTodos();

	void eliminar(int idControl);

}
