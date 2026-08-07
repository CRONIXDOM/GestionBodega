package com.andiana.api.dominio.modelo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Cuanta materia prima lleva una receta. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecetaDetalle {

    private Integer idRecetaDetalle;
    private Integer idReceta;
    private Integer idMateriaPrima;
    private BigDecimal cantidad;
}
