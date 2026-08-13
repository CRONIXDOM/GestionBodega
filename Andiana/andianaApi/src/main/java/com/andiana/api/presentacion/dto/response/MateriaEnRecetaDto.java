package com.andiana.api.presentacion.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MateriaEnRecetaDto {

	private Integer idMateria;
	private String materiaPrima;
	private BigDecimal cantidad;
	private String unidad;
	private BigDecimal stockActual;
}
