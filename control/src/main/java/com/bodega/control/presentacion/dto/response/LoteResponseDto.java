package com.bodega.control.presentacion.dto.response;

import java.time.LocalDate;

import lombok.Data;
@Data
public class LoteResponseDto {
	private Integer idLote;
	private String numeroLote;
	private LocalDate fechaIngreso;
	private LocalDate fechaVencimiento;
	private String cantidadLote;
	private Integer idProducto;

}
