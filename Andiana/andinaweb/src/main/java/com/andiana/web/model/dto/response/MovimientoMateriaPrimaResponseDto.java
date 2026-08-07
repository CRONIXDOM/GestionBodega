package com.andiana.web.model.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MovimientoMateriaPrimaResponseDto {

    private Integer idMovimiento;
    private Integer idMateria;
    private LocalDateTime fecha;
    private String tipo;
    private BigDecimal cantidad;
    private String observacion;
}
