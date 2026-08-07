package com.andiana.api.dominio.modelo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Cada tanda que sale de una orden; una orden puede dar varios lotes. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoteProduccion {

    private Integer idLote;
    private String codigoLote;
    private Integer idOrden;
    private Integer cantidadProducida;
    private LocalDate fechaFabricacion;
}
