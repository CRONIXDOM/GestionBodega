package com.bodega.control.presentacion.dto.request;

import lombok.Data;

@Data
public class DetalleEntregaRequestDto {

	private Integer idDetalleEntrega;
	private Integer idProducto;
	private Integer cantidadProducto;
	private String codigoEvento;
	private String nombreEvento;
	private Integer idEntrega;
	// si viene informado, se descuenta exactamente lo reservado para ese pedido
	// (idProducto/cantidadProducto se ignoran porque ya quedaron fijados en la Solicitud)
	private Integer idDetalleSolicitud;
	// opcional: forzar que la salida (sin pedido previo) se tome de este lote puntual
	// en vez de dejar que el sistema elija el mas antiguo (FIFO)
	private Integer idLote;

}
