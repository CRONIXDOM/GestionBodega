package com.andiana.api.presentacion.dto.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DetalleRecetaResponseDto {

	private Integer idDetalle;
	private Integer idReceta;
	private Integer idMateria;
	private BigDecimal cantidad;
	private String unidad;
}
