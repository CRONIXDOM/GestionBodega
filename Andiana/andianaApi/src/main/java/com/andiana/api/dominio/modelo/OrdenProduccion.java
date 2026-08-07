package com.andiana.api.dominio.modelo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Lo que planificacion manda a fabricar. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenProduccion {

    private Integer idOrden;
    private String codigo;
    private Integer idProducto;
    private Integer cantidadProgramada;
    private LocalDate fechaProduccion;
    private String estado;
}
