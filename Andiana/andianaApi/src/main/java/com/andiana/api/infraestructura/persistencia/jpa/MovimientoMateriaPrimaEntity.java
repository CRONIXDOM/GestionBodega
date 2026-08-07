package com.andiana.api.infraestructura.persistencia.jpa;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/** Reflejo exacto de la tabla movimiento_materia_prima, tal y como ya existe en la base. */
@Entity
@Data
@Table(name = "movimiento_materia_prima")
public class MovimientoMateriaPrimaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_movimiento")
	private Integer idMovimiento;
	@Column(name = "id_materia")
	private Integer idMateria;
	@Column(name = "fecha")
	private LocalDateTime fecha;
	@Column(name = "tipo", length = 20)
	private String tipo;
	@Column(name = "cantidad")
	private BigDecimal cantidad;
	@Column(name = "observacion")
	private String observacion;
}
