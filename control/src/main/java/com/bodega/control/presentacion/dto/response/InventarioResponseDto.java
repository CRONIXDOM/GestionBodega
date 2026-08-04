package com.bodega.control.presentacion.dto.response;

import lombok.Data;

@Data
public class InventarioResponseDto {
	private Integer idProducto;
	private String nombreProducto;
	private String codigoProducto;
	private int numeroLotes;
	private int cantidadTotal;
	private int cantidadReservada;
	private int cantidadDisponible;
}
