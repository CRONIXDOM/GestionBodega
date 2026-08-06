package com.bodega.controlweb.model.dto.request;

import java.time.LocalDate;

import lombok.Data;

/**
 * Formulario único de Bodegas: reúne en una sola pantalla los datos de la sede,
 * la zona y la ubicación, que antes obligaban a pasar por tres formularios
 * distintos para registrar un mismo sitio de almacenamiento.
 */
@Data
public class BodegaRequestDto {

    // ubicación (el sitio concreto)
    private Integer idUbicacion;
    private String codigoUbicacion;
    private LocalDate fechaUbicacion;

    // sede: se elige una existente o se crea con estos datos
    private Integer idSede;
    private String nombreSede;
    private String direccion;
    private String descripcion;
    private Integer capacidad;

    // zona: se elige una existente o se crea escribiendo su nombre
    private Integer idZona;
    private String nombreZona;
    private String descripcionZona;
}
