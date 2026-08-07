package com.andiana.api.dominio.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Cada tanda que sale de una orden; una orden puede dar varios lotes. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoteProduccion {

	private Integer idLote;
	private Integer idOrden;
	private String numeroLote;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private BigDecimal cantidadProducida;
	private String estado;
}
