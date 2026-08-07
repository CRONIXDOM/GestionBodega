package com.andiana.web.model.dto.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MateriaPrimaRequestDto {

    private Integer idMateria;
    private String nombre;
    private String unidadMedida;
    private BigDecimal stockActual;
    private BigDecimal stockMinimo;
}
