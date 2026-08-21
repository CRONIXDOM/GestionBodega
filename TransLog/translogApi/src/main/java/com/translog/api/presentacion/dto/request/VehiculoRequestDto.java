package com.translog.api.presentacion.dto.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class VehiculoRequestDto {

	private Integer idVehiculo;
	private String placa;
	private BigDecimal capacidadMaxima;
	private String estado;
}
