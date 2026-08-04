package com.bodega.controlweb.model.dto.request;

import lombok.Data;

@Data
public class ProductoRequestDto {

    private Integer idProducto;
    private String nombreProducto;
    private String codigoProducto;
    private String cantidadProducto;
}
