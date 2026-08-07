package com.andiana.api.infraestructura.entidad;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/** Reflejo exacto de la tabla receta_detalle, tal y como ya existe en la base. */
@Data
@Entity
@Table(name = "receta_detalle")
public class RecetaDetalleEntidad {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_receta_detalle")
	private Integer idRecetaDetalle;
	@Column(name = "id_receta")
	private Integer idReceta;
	@Column(name = "id_materia_prima")
	private Integer idMateriaPrima;
	@Column(name = "cantidad")
	private BigDecimal cantidad;
}
