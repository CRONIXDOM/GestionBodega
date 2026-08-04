package com.bodega.controlweb.model.dto.response;

import java.time.LocalDate;
import lombok.Data;

@Data
public class SolicitudResponseDto {

    private Integer idSolicitud;
    private LocalDate fechaSolicitud;
    private Boolean estadoSolicitud;
    private Integer idUsuarioRol;
}
