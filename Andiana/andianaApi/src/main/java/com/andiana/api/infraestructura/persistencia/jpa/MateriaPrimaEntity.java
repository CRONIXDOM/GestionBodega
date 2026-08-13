package com.andiana.api.infraestructura.persistencia.jpa;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "materia_prima")
public class MateriaPrimaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_materia")
	private Integer idMateria;
	@Column(name = "nombre", length = 120)
	private String nombre;
	@Column(name = "unidad_medida", length = 20)
	private String unidadMedida;
	@Column(name = "stock_actual")
	private BigDecimal stockActual;
	@Column(name = "stock_minimo")
	private BigDecimal stockMinimo;
}
