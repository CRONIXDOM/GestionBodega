package com.bodega.controlweb.model.dto.request;

import lombok.Data;

/**
 * Formulario de Bodegas. Para el usuario una bodega es una sola cosa: el local
 * con su capacidad y la zona en la que se encuentra. Por debajo esos datos se
 * reparten entre sede, zona y ubicación, pero eso no se le pide en pantalla.
 */
@Data
public class BodegaRequestDto {

    private Integer idSede;
    private String nombreSede;
    private String direccion;
    private String descripcion;
    private Integer capacidad;

    /** Se escribe libremente; si ya existe una zona con ese nombre se reutiliza. */
    private String nombreZona;
}
