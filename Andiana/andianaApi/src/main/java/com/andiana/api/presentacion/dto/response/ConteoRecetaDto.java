package com.andiana.api.presentacion.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConteoRecetaDto {

	private Integer idReceta;
	private String producto;
	private Integer version;
	private Boolean activa;
	private Long totalMateriasPrimas;
}
