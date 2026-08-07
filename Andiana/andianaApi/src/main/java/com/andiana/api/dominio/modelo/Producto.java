package com.andiana.api.dominio.modelo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Una bebida en una presentacion concreta (350 ML, 2 LITROS...). */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    private Integer idProducto;
    private String nombre;
    private String presentacion;
}
