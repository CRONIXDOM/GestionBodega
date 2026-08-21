package com.translog.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.translog.api.dominio.entidades.Ruta;

public interface IRutaRepositorio {

	Ruta guardar(Ruta nuevoRuta);

	Optional<Ruta> buscarPorId(int idRuta);

	List<Ruta> listarTodos();

	void eliminar(int idRuta);

}
