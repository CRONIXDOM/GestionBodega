package com.andiana.web.model.dto.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MateriaPrimaResponseDto {

    private Integer idMateria;
    private String nombre;
    private String unidadMedida;
    private BigDecimal stockActual;
    private BigDecimal stockMinimo;
}
