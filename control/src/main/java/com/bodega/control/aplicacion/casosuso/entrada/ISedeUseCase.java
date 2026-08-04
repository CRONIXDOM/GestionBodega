package com.bodega.control.aplicacion.casosuso.entrada;

import java.util.List;

import com.bodega.control.dominio.entidades.Sede;

public interface ISedeUseCase {

	Sede guardar(Sede nuevaSede);

	Sede buscarPorId(int idSede);

	List<Sede> listarTodos();

	void eliminar(int idSede);

}
