package com.andiana.api.dominio.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Una bebida en una presentacion concreta. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

	private Integer idProducto;
	private String nombre;
	private String tipo;
	private String presentacion;
	private Integer volumenMl;
	private Boolean estado;
}
