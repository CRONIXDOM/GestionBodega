package com.andiana.api.dominio.modelo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Formula de un producto. Un producto tiene varias porque la formula cambia con el tiempo. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receta {

    private Integer idReceta;
    private Integer idProducto;
    private String version;
    private LocalDate fecha;
    private Boolean activa;
}
