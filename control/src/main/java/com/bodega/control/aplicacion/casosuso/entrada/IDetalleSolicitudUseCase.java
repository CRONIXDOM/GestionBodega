package com.bodega.control.aplicacion.casosuso.entrada;

import java.util.List;

import com.bodega.control.dominio.entidades.DetalleSolicitud;
import com.bodega.control.dominio.entidades.DetalleSolicitudLote;

public interface IDetalleSolicitudUseCase {

	DetalleSolicitud guardar (DetalleSolicitud nuevaDetalleSolicitud, Integer idLoteManual);

	DetalleSolicitud buscarPorid (int DetalleSolicitud);

	List<DetalleSolicitud> listarTodos();

	void eliminar (int DetalleSolicitud);

	DetalleSolicitud buscarPorId(int idDetalleSolicitud);

	List<DetalleSolicitudLote> obtenerAsignaciones(int idDetalleSolicitud);

}
