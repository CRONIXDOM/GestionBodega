package com.andiana.api.dominio.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Lo que planificacion manda a fabricar. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenProduccion {

	private Integer idOrden;
	private Integer idProducto;
	private LocalDate fechaProgramada;
	private BigDecimal cantidadProgramada;
	private String estado;
	private String responsable;
}
