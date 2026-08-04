package com.bodega.controlweb.model.dto.request;

import lombok.Data;

@Data
public class UsuarioRequestDto {

    private Integer idUsuario;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String estado;
}
