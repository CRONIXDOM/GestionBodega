package com.andiana.api.presentacion.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class InventarioProductoRequestDto {

	private Integer idInventario;
	private Integer idLote;
	private BigDecimal cantidad;
	private String ubicacion;
	private LocalDate fechaIngreso;
}
