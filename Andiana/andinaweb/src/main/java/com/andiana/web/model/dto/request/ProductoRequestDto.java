package com.andiana.web.model.dto.request;

import lombok.Data;

@Data
public class ProductoRequestDto {

    private Integer idProducto;
    private String nombre;
    private String tipo;
    private String presentacion;
    private Integer volumenMl;
    private Boolean estado;
}
