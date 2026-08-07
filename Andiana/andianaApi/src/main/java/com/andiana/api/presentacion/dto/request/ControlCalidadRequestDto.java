package com.andiana.api.presentacion.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ControlCalidadRequestDto {

	private Integer idControl;
	private Integer idLote;
	private LocalDateTime fechaControl;
	private BigDecimal ph;
	private BigDecimal brix;
	private BigDecimal temperatura;
	private String resultado;
	private String observaciones;
}
