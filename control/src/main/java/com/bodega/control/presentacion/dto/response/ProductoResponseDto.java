package com.bodega.control.presentacion.dto.response;

import lombok.Data;

@Data
public class ProductoResponseDto {
	private Integer idProducto;
	private String nombreProducto;
	private String codigoProducto;
	private String cantidadProducto;
	private Integer unidadesPorCaja;	

}
