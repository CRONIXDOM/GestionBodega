package com.bodega.control.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Solicitud;

public interface ISolicitudRepositorio {
	
	Solicitud guardar (Solicitud nuevaSolicitud);
	
	Optional<Solicitud> buscarPorid (int Solicitud);
	
	List<Solicitud> listarTodos();
	
	void eliminar (int Solicitud);
}
