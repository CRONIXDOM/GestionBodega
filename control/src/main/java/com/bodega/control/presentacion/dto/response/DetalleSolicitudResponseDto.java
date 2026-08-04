package com.bodega.control.presentacion.dto.response;

import lombok.Data;

@Data
public class DetalleSolicitudResponseDto {
	private Integer idDetalleSolicitud;
	private Integer cantidadProducto;
	private String lugarRecogida;
	private Integer idProducto;
	private Integer idSolicitud;

}
