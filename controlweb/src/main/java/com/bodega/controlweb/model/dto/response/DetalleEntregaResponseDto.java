package com.bodega.controlweb.model.dto.response;

import lombok.Data;

@Data
public class DetalleEntregaResponseDto {

    private Integer idDetalleEntrega;
    private Integer idProducto;
    private Integer cantidadProducto;
    private String codigoEvento;
    private String nombreEvento;
    private Integer idEntrega;
    private Integer idDetalleSolicitud;
    private Integer idLote;
}
