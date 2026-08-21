package com.translog.web.model.dto.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DespachoConEnviosDto {
    private Integer idDespacho;
    private LocalDate fechaDespacho;
    private Long cantidadEnvios;
    private String ruta;
    private String vehiculo;
    private String conductor;
    private String estado;
}
