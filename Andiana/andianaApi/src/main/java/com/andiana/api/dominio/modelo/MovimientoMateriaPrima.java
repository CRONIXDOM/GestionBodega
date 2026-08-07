package com.andiana.api.dominio.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Ingreso, consumo o ajuste del stock de una materia prima. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoMateriaPrima {

	private Integer idMovimiento;
	private Integer idMateria;
	private LocalDateTime fecha;
	private String tipo;
	private BigDecimal cantidad;
	private String observacion;
}
