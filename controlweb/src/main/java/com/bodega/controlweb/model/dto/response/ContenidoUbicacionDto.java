package com.bodega.controlweb.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/** Una línea de "qué hay guardado" dentro de una ubicación. */
@Data
@AllArgsConstructor
public class ContenidoUbicacionDto {

    private Integer idProducto;
    private String codigoProducto;
    private String nombreProducto;
    private Integer cantidad;
}
