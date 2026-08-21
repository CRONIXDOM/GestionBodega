package com.translog.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.translog.api.dominio.entidades.Conductor;

public interface IConductorRepositorio {

	Conductor guardar(Conductor nuevoConductor);

	Optional<Conductor> buscarPorId(int idConductor);

	List<Conductor> listarTodos();

	void eliminar(int idConductor);

}
