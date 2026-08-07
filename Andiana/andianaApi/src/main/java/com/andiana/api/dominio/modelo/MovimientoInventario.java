package com.andiana.api.dominio.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Entrada, salida o correccion del stock de una materia prima. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoInventario {

    private Integer idMovimiento;
    private Integer idMateriaPrima;
    private String tipo;
    private BigDecimal cantidad;
    private LocalDate fecha;
    private String observacion;
}
