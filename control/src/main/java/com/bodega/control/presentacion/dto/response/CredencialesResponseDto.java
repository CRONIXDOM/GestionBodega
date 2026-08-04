package com.bodega.control.presentacion.dto.response;

import lombok.Data;

@Data
public class CredencialesResponseDto {
	
	private Integer idCredenciales;
	private String usuario;
	private String correo;
	private String contrasena;
}
