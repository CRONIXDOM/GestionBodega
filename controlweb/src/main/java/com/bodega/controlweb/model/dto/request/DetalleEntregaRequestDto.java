package com.bodega.controlweb.model.dto.request;

import lombok.Data;

@Data
public class DetalleEntregaRequestDto {

    private Integer idDetalleEntrega;
    private String nombreProducto;
    private String cantidadProducto;
    private String codigoEvento;
    private String nombreEvento;
}
