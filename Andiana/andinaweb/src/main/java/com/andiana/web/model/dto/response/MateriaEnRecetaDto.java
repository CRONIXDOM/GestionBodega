package com.andiana.web.model.dto.response;

import java.math.BigDecimal;

import lombok.Data;

/** Una línea de la consulta "materias primas utilizadas en una receta". */
@Data
public class MateriaEnRecetaDto {

    private Integer idMateria;
    private String materiaPrima;
    private BigDecimal cantidad;
    private String unidad;
    private BigDecimal stockActual;
}
