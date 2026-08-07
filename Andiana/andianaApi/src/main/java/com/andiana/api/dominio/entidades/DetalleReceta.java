package com.andiana.api.dominio.entidades;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Cuanta materia prima lleva una receta. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleReceta {

	private Integer idDetalle;
	private Integer idReceta;
	private Integer idMateria;
	private BigDecimal cantidad;
	private String unidad;
}
