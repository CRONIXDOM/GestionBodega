package com.bodega.controlweb.model.dto.response;

import lombok.Data;

@Data
public class ProductoResponseDto {

    private Integer idProducto;
    private String nombreProducto;
    private String codigoProducto;
    private String cantidadProducto;
}
