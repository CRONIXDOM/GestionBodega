package com.andiana.api.dominio.entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Lo que mide el laboratorio al terminar un lote. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControlCalidad {

	private Integer idControl;
	private Integer idLote;
	private LocalDateTime fechaControl;
	private BigDecimal ph;
	private BigDecimal brix;
	private BigDecimal temperatura;
	private String resultado;
	private String observaciones;
}
