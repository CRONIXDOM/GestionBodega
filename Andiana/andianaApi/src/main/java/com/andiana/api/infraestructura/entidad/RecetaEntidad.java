package com.andiana.api.infraestructura.entidad;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/** Reflejo exacto de la tabla receta, tal y como ya existe en la base. */
@Data
@Entity
@Table(name = "receta")
public class RecetaEntidad {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_receta")
	private Integer idReceta;
	@Column(name = "id_producto")
	private Integer idProducto;
	@Column(name = "version")
	private String version;
	@Column(name = "fecha")
	private LocalDate fecha;
	@Column(name = "activa")
	private Boolean activa;
}
