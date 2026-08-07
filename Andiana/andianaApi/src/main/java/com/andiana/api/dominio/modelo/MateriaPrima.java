package com.andiana.api.dominio.modelo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Insumo de bodega. El stock no se edita a mano: lo mueven los movimientos. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MateriaPrima {

	private Integer idMateria;
	private String nombre;
	private String unidadMedida;
	private BigDecimal stockActual;
	private BigDecimal stockMinimo;
}
