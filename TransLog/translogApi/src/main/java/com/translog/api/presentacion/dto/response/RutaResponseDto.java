package com.translog.api.presentacion.dto.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RutaResponseDto {

	private Integer idRuta;
	private Integer idCiudadOrigen;
	private Integer idCiudadDestino;
	private BigDecimal distanciaKm;
}
