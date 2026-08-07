package com.andiana.api.infraestructura.entidad;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/** Reflejo exacto de la tabla movimiento_inventario, tal y como ya existe en la base. */
@Data
@Entity
@Table(name = "movimiento_inventario")
public class MovimientoInventarioEntidad {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_movimiento")
	private Integer idMovimiento;
	@Column(name = "id_materia_prima")
	private Integer idMateriaPrima;
	@Column(name = "tipo")
	private String tipo;
	@Column(name = "cantidad")
	private BigDecimal cantidad;
	@Column(name = "fecha")
	private LocalDate fecha;
	@Column(name = "observacion")
	private String observacion;
}
