package com.andiana.api.dominio.modelo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Donde queda guardado un lote aprobado. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlmacenProductoTerminado {

    private Integer idAlmacen;
    private Integer idLote;
    private Integer cantidad;
    private String ubicacionFisica;
    private LocalDate fechaIngreso;
}
