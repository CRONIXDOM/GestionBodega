package com.andiana.api.infraestructura.entidad;

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
@Data
@Entity
@Table(name = "movimiento_materia_prima")
public class MovimientoMateriaPrimaEntidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_movimiento")
	private Integer idMovimiento;
	@Column(name = "id_materia")
	private Integer idMateria;
	@Column(name = "fecha")
	private LocalDateTime fecha;
	@Column(name = "tipo")
	private String tipo;
	@Column(name = "cantidad")
	private BigDecimal cantidad;
	@Column(name = "observacion")
	private String observacion;
}
