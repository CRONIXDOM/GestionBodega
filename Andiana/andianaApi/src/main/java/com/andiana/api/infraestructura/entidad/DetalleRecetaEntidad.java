package com.andiana.api.infraestructura.entidad;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/** Reflejo exacto de la tabla detalle_receta, tal y como ya existe en la base. */
@Data
@Entity
@Table(name = "detalle_receta")
public class DetalleRecetaEntidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_detalle")
	private Integer idDetalle;
	@Column(name = "id_receta")
	private Integer idReceta;
	@Column(name = "id_materia")
	private Integer idMateria;
	@Column(name = "cantidad")
	private BigDecimal cantidad;
	@Column(name = "unidad")
	private String unidad;
}
