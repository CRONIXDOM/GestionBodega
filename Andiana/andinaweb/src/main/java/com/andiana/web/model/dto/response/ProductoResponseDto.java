package com.andiana.web.model.dto.response;

import lombok.Data;

@Data
public class ProductoResponseDto {

    private Integer idProducto;
    private String nombre;
    private String tipo;
    private String presentacion;
    private Integer volumenMl;
    private Boolean estado;
}
