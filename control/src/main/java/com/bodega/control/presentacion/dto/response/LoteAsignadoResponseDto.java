package com.bodega.control.presentacion.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoteAsignadoResponseDto {
	private Integer idLote;
	private String numeroLote;
	private Integer cantidad;
}
