package com.bodega.control.presentacion.dto.response;

import lombok.Data;

@Data
public class UsuarioResponseDto {
    private Integer idUsuario;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String estado;

}
