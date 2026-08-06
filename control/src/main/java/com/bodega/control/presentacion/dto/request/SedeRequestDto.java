package com.bodega.control.presentacion.dto.request;

import lombok.Data;

@Data
public class SedeRequestDto {
	private Integer idSede;
	private String nombreSede;
	private String direccion;
	private String descripcion;
	private Integer capacidad;

}
