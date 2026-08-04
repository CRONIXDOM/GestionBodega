package com.bodega.control.presentacion.dto.response;

import java.time.LocalDate;

import lombok.Data;
@Data
public class LoteResponseDto {
	private Integer idLote;
	private String numeroLote;
	private LocalDate fechaIngreso;
	private LocalDate fechaVencimiento;
	private Integer cantidadLote;
	private Integer cantidadReservada;
	private Integer cantidadDisponible;
	private Integer idProducto;
	private Integer idUbicacion;

}
