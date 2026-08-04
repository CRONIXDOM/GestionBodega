package com.bodega.control.presentacion.dto.request;

import lombok.Data;

@Data
public class DetalleSolicitudRequestDto {

	private Integer idDetalleSolicitud;
	private Integer cantidadProducto;
	private String lugarRecogida;
	private Integer idProducto;
	private Integer idSolicitud;
	// opcional: reservar de este lote puntual en vez de dejar que el sistema elija
	// el mas antiguo (FIFO) automaticamente
	private Integer idLote;

}
