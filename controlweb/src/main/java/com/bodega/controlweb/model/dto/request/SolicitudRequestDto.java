package com.bodega.controlweb.model.dto.request;

import java.time.LocalDate;
import lombok.Data;

@Data
public class SolicitudRequestDto {

    private Integer idSolicitud;
    private LocalDate fechaSolicitud;
    private Boolean estadoSolicitud;
    private Integer idUsuarioRol;
}
