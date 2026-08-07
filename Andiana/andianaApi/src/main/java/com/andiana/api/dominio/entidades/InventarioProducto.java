package com.andiana.api.dominio.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Producto terminado guardado en el almacen. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioProducto {

	private Integer idInventario;
	private Integer idLote;
	private BigDecimal cantidad;
	private String ubicacion;
	private LocalDate fechaIngreso;
}
