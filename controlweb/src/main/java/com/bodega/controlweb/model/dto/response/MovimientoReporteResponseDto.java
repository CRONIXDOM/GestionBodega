package com.bodega.controlweb.model.dto.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MovimientoReporteResponseDto {
    private Integer idRegistro;
    private LocalDate fechaRegistro;
    private String numeroLote;
    private String nombreProducto;
    private Integer idTipo;
    private String tipoMovimiento;
    private String claseMovimiento;
    private String codigoUbicacion;
    private String nombreZona;
    private Integer idSede;
    private String nombreSede;
    private String nombreUsuario;
}
