package com.bodega.control.presentacion.dto.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EntregaRequestDto {
	private Integer idEntrega;
	private LocalDate fechaEntrega;
	private String responsableEntrega;

}
