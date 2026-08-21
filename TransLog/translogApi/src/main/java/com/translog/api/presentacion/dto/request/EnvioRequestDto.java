package com.translog.api.presentacion.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class EnvioRequestDto {

	private Integer idEnvio;
	private Integer idCiudadOrigen;
	private Integer idCiudadDestino;
	private BigDecimal peso;
	private LocalDate fechaRegistro;
	private BigDecimal valorDeclarado;
	private String estado;
	private Integer idDespacho;
}
