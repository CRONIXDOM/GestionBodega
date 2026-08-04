package com.bodega.control.aplicacion.casosuso.entrada;

import java.util.List;

import com.bodega.control.dominio.entidades.Solicitud;

public interface ISolicitudUseCase {
	
	Solicitud guardar (Solicitud nuevaSolicitud);
	
	Solicitud buscarPorid (int Solicitud);
	
	List<Solicitud> listarTodos();
	
	void eliminar (int Solicitud);

	Solicitud buscarPorId(int idSolicitud);

}
