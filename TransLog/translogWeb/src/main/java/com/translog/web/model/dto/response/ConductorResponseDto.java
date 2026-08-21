package com.translog.web.model.dto.response;

import lombok.Data;

@Data
public class ConductorResponseDto {

	private Integer idConductor;
	private String nombre;
	private String licencia;
	private String estado;
}
