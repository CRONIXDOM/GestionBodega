package com.bodega.control.presentacion.dto.request;

import java.time.LocalDate;

import lombok.Data;
@Data
public class ReporteRequestDto {
	private Integer idReporte;
	private String tipoReporte;
	private LocalDate fechaCreacion;
	private Integer idUsuarioRol;

}
