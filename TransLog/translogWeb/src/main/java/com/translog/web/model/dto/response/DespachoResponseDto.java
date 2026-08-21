package com.translog.web.model.dto.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DespachoResponseDto {

	private Integer idDespacho;
	private LocalDate fechaDespacho;
	private Integer idRuta;
	private Integer idVehiculo;
	private Integer idConductor;
	private String estado;
}
