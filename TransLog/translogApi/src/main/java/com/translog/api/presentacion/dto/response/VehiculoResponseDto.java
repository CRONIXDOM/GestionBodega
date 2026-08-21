package com.translog.api.presentacion.dto.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class VehiculoResponseDto {

	private Integer idVehiculo;
	private String placa;
	private BigDecimal capacidadMaxima;
	private String estado;
}
