package com.bodega.control.presentacion.dto.response;

import java.time.LocalDate;

import lombok.Data;
@Data
public class RegistroResponseDto {
	private Integer idRegistro;
	private LocalDate fechaRegistro;
	private Integer idLote;
	private Integer idTipo;
	private Integer idUbicacion;
	private Integer idDetalleEntrega;
	private Integer idUsuarioRol;

}
