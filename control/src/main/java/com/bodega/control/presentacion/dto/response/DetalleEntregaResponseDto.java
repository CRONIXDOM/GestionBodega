package com.bodega.control.presentacion.dto.response;

import lombok.Data;

@Data
public class DetalleEntregaResponseDto {
	private Integer idDetalleEntrega;
	private String nombreProducto;
	private String cantidadProducto;
	private String codigoEvento;
	private String nombreEvento;


}
