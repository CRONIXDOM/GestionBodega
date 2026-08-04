package com.bodega.controlweb.model.dto.request;

import lombok.Data;

@Data
public class SedeRequestDto {

    private Integer idSede;
    private String nombreSede;
    private String direccion;
    private String descripcion;
}
