package com.bodega.control.presentacion.dto.request;

import lombok.Data;

@Data
public class CredencialesRequestDto {
	
	private Integer idCredenciales;
	private String usuario;
	private String correo;
	private String contrasena;
	private Boolean contrasenaTemporal;


}
