package com.andiana.api.infraestructura.entidad;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/** Reflejo exacto de la tabla receta_produccion, tal y como ya existe en la base. */
@Data
@Entity
@Table(name = "receta_produccion")
public class RecetaProduccionEntidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_receta")
	private Integer idReceta;
	@Column(name = "id_producto")
	private Integer idProducto;
	@Column(name = "version")
	private Integer version;
	@Column(name = "fecha_vigencia")
	private LocalDate fechaVigencia;
	@Column(name = "estado")
	private Boolean estado;
}
