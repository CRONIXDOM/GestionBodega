package com.translog.api.presentacion.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Una linea del ranking de despachos: la fecha, cuantos envios lleva y por que
 * ruta va, que es lo que pide la gerencia.
 */
@Data
@AllArgsConstructor
public class DespachoConEnviosDto {

	private Integer idDespacho;
	private LocalDate fechaDespacho;
	private Long cantidadEnvios;
	private String ruta;
	private String vehiculo;
	private String conductor;
	private String estado;
}
