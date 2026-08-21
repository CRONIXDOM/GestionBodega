package com.translog.web.model.dto.request;

import lombok.Data;

@Data
public class ConductorRequestDto {

	private Integer idConductor;
	private String nombre;
	private String licencia;
	private String estado;
}
