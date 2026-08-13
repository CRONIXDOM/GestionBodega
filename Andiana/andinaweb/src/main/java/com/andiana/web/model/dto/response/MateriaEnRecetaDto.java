package com.andiana.web.model.dto.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MateriaEnRecetaDto {

    private Integer idMateria;
    private String materiaPrima;
    private BigDecimal cantidad;
    private String unidad;
    private BigDecimal stockActual;
}
