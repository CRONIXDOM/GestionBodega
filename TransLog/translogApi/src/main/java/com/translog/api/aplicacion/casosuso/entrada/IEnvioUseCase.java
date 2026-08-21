package com.translog.api.aplicacion.casosuso.entrada;

import java.util.List;

import com.translog.api.dominio.entidades.Envio;

public interface IEnvioUseCase {

	Envio guardar(Envio nuevoEnvio);

	Envio buscarPorId(int idEnvio);

	List<Envio> listarTodos();

	void eliminar(int idEnvio);

}
