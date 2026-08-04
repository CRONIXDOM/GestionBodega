package com.bodega.control.presentacion.dto.request;

import java.time.LocalDate;

import lombok.Data;
@Data
public class UbicacionRequestDto {
	private Integer idUbicacion;
	private String cantidadUbicacion;
	private LocalDate fechaUbicacion;

}
