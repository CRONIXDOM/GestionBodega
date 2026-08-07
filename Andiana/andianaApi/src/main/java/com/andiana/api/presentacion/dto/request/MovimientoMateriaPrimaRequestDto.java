package com.andiana.api.presentacion.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MovimientoMateriaPrimaRequestDto {

	private Integer idMovimiento;
	private Integer idMateria;
	private LocalDateTime fecha;
	private String tipo;
	private BigDecimal cantidad;
	private String observacion;
}
