package com.bodega.controlweb.model.dto.request;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RegistroRequestDto {

    private Integer idRegistro;
    private LocalDate fechaRegistro;
    private Integer idLote;
    private Integer idTipo;
    private Integer idUbicacion;
    private Integer idDetalleEntrega;
    private Integer idUsuarioRol;
}
