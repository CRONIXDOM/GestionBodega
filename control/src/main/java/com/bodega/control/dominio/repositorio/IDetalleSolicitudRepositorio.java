package com.bodega.control.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.DetalleSolicitud;

public interface IDetalleSolicitudRepositorio {
	
	DetalleSolicitud guardar (DetalleSolicitud nuevaDetalleSolicitud);
	
	Optional<DetalleSolicitud> buscarPorid (int DetalleSolicitud);
	
	List<DetalleSolicitud> listarTodos();
	
	void eliminar (int DetalleSolicitud);

}
