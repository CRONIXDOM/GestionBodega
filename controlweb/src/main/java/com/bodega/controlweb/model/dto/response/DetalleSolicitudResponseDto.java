package com.bodega.controlweb.model.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class DetalleSolicitudResponseDto {

    private Integer idDetalleSolicitud;
    private Integer cantidadProducto;
    private String lugarRecogida;
    private Integer idProducto;
    private Integer idSolicitud;
    private List<LoteAsignadoResponseDto> lotesAsignados;
}
