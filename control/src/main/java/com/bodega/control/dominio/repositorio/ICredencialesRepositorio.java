package com.bodega.control.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Credenciales;

public interface ICredencialesRepositorio {
	
	Credenciales guardar(Credenciales nuevaCredenciales);

	Optional<Credenciales> buscarPorId(int idCredenciales);

	List<Credenciales> listarTodos();

	void eliminar(int idCredenciales);

	List<Credenciales> listarCredenciales();

	List<Credenciales> buscarCredencialesNombre(String nombre);

	List<Credenciales> buscarCredencialesEstado(String nombre, boolean estado);
	
}	