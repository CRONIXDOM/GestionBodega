package com.bodega.controlweb.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Una línea del detalle de una bodega: qué producto guarda, cuántas unidades y
 * en cuántos lotes están repartidas esas unidades.
 */
@Data
@AllArgsConstructor
public class ContenidoSedeDto {

    private Integer idProducto;
    private String codigoProducto;
    private String nombreProducto;
    private Integer cantidad;
    private Integer lotes;
}
