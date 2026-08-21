package com.translog.api.presentacion.dto.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RutaRequestDto {

	private Integer idRuta;
	private Integer idCiudadOrigen;
	private Integer idCiudadDestino;
	private BigDecimal distanciaKm;
}
