package com.bodega.control.aplicacion.casosuso.entrada;

import java.util.List;

import com.bodega.control.dominio.entidades.Credenciales;


public interface ICredencialesUseCase {
	
	Credenciales guardar(Credenciales nuevaCredenciales);
	
	Credenciales buscarPorId(int idCredenciales);
	
	List<Credenciales> listarTodos();
	
	void eliminar (int idCredenciales);

	List<Credenciales> listarCredenciales();

	List<Credenciales> buscarCredencialesNombre(String nombre);

	List<Credenciales> buscarCredencialesEstado(String nombre, boolean estado);
}
