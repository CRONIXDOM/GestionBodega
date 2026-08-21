package com.translog.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.translog.api.dominio.entidades.Envio;

public interface IEnvioRepositorio {

	Envio guardar(Envio nuevoEnvio);

	Optional<Envio> buscarPorId(int idEnvio);

	List<Envio> listarTodos();

	void eliminar(int idEnvio);

}
