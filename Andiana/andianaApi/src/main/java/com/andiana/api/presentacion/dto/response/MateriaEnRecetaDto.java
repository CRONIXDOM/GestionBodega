package com.andiana.api.presentacion.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Una linea de la consulta "materias primas utilizadas en una receta". */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MateriaEnRecetaDto {

	private Integer idMateria;
	private String materiaPrima;
	private BigDecimal cantidad;
	private String unidad;
	private BigDecimal stockActual;
}
