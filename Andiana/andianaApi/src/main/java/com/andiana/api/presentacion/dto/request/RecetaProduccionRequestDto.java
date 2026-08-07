package com.andiana.api.presentacion.dto.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RecetaProduccionRequestDto {

	private Integer idReceta;
	private Integer idProducto;
	private Integer version;
	private LocalDate fechaVigencia;
	private Boolean estado;
}
