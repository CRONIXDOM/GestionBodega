package com.bodega.controlweb.model.dto.response;

import lombok.Data;

@Data
public class InventarioResponseDto {

    private Integer idProducto;
    private String nombreProducto;
    private String codigoProducto;
    private Integer numeroLotes;
    private Integer cantidadTotal;
    private Integer cantidadReservada;
    private Integer cantidadDisponible;
}
