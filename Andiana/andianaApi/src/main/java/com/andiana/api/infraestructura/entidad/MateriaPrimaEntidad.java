package com.andiana.api.infraestructura.entidad;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/** Reflejo exacto de la tabla materia_prima, tal y como ya existe en la base. */
@Data
@Entity
@Table(name = "materia_prima")
public class MateriaPrimaEntidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_materia")
	private Integer idMateria;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "unidad_medida")
	private String unidadMedida;
	@Column(name = "stock_actual")
	private BigDecimal stockActual;
	@Column(name = "stock_minimo")
	private BigDecimal stockMinimo;
}
