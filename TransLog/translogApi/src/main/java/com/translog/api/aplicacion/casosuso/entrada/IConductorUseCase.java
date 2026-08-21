package com.translog.api.aplicacion.casosuso.entrada;

import java.util.List;

import com.translog.api.dominio.entidades.Conductor;

public interface IConductorUseCase {

	Conductor guardar(Conductor nuevoConductor);

	Conductor buscarPorId(int idConductor);

	List<Conductor> listarTodos();

	void eliminar(int idConductor);

}
