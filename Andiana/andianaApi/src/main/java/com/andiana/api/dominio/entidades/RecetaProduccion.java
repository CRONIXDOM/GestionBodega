package com.andiana.api.dominio.entidades;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Formula de un producto. Un producto tiene varias versiones porque la formula cambia. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecetaProduccion {

	private Integer idReceta;
	private Integer idProducto;
	private Integer version;
	private LocalDate fechaVigencia;
	private Boolean estado;
}
