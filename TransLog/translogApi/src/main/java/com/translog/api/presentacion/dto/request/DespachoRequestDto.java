package com.translog.api.presentacion.dto.request;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class DespachoRequestDto {

	private Integer idDespacho;
	private LocalDate fechaDespacho;
	private Integer idRuta;
	private Integer idVehiculo;
	private Integer idConductor;
	private String estado;
	/** Los envios que va a transportar: sin ellos la regla no se puede comprobar. */
	private List<Integer> idsDeEnvios;
}
