package com.bodega.control.presentacion.dto.request;

import lombok.Data;

@Data
public class DetalleSolicitudRequestDto {
	
	private Integer idDetalleSolicitud;
	private Integer cantidadProducto;
	private String lugarRecogida;
	private Integer idProducto;

}
