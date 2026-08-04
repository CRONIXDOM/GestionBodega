package com.bodega.control.presentacion.dto.request;

import lombok.Data;

@Data
public class RolRequestDto {
	private Integer idRol;
	private String nombreRol;
	private String descripcionRol;
	private String modulos;

}
