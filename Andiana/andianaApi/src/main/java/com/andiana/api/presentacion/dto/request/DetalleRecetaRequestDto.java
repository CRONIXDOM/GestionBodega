package com.andiana.api.presentacion.dto.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DetalleRecetaRequestDto {

	private Integer idDetalle;
	private Integer idReceta;
	private Integer idMateria;
	private BigDecimal cantidad;
	private String unidad;
}
