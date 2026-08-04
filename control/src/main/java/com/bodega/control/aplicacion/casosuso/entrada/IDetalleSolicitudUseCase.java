package com.bodega.control.aplicacion.casosuso.entrada;

import java.util.List;

import com.bodega.control.dominio.entidades.DetalleSolicitud;

public interface IDetalleSolicitudUseCase {
	
	DetalleSolicitud guardar (DetalleSolicitud nuevaDetalleSolicitud);
	
	DetalleSolicitud buscarPorid (int DetalleSolicitud);
	
	List<DetalleSolicitud> listarTodos();
	
	void eliminar (int DetalleSolicitud);

	DetalleSolicitud buscarPorId(int idDetalleSolicitud);

}
