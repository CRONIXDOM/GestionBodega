package com.bodega.controlweb.model.dto.response;

import lombok.Data;

@Data
public class RolResponseDto {

    private Integer idRol;
    private String nombreRol;
    private String descripcionRol;
    private String modulos;
}
