package com.bodega.control.dominio.repositorio;

import java.util.List;

import com.bodega.control.dominio.entidades.DetalleSolicitudLote;

public interface IDetalleSolicitudLoteRepositorio {

	DetalleSolicitudLote guardar(DetalleSolicitudLote asignacion);

	List<DetalleSolicitudLote> buscarPorDetalleSolicitud(int idDetalleSolicitud);

}
