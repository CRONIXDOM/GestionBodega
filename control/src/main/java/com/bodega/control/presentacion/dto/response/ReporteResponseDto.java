package com.bodega.control.presentacion.dto.response;

import java.time.LocalDate;

import lombok.Data;
@Data
public class ReporteResponseDto {
	private Integer idReporte;
	private String tipoReporte;
	private LocalDate fechaCreacion;
	private Integer idUsuarioRol;

}
