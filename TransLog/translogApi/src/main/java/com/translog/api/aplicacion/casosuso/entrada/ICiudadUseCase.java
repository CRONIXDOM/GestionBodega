package com.translog.api.aplicacion.casosuso.entrada;

import java.util.List;

import com.translog.api.dominio.entidades.Ciudad;

public interface ICiudadUseCase {

	Ciudad guardar(Ciudad nuevoCiudad);

	Ciudad buscarPorId(int idCiudad);

	List<Ciudad> listarTodos();

	void eliminar(int idCiudad);

}
