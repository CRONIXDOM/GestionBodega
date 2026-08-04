package com.bodega.controlweb.model.dto.request;

import lombok.Data;

@Data
public class CredencialesRequestDto {

    private Integer idCredenciales;
    private String usuario;
    private String correo;
    private String contrasena;
}
