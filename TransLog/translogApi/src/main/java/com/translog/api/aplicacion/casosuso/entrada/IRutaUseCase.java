package com.translog.api.aplicacion.casosuso.entrada;

import java.util.List;

import com.translog.api.dominio.entidades.Ruta;

public interface IRutaUseCase {

	Ruta guardar(Ruta nuevoRuta);

	Ruta buscarPorId(int idRuta);

	List<Ruta> listarTodos();

	void eliminar(int idRuta);

}
