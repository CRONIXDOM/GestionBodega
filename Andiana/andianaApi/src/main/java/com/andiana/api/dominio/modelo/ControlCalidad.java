package com.andiana.api.dominio.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Lo que mide el laboratorio al terminar un lote. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControlCalidad {

    private Integer idControl;
    private Integer idLote;
    private BigDecimal ph;
    private BigDecimal gradosBrix;
    private BigDecimal temperatura;
    private String resultado;
    private LocalDate fechaInspeccion;
}
