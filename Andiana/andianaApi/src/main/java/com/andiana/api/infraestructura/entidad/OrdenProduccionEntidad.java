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

/** Reflejo exacto de la tabla orden_produccion, tal y como ya existe en la base. */
@Data
@Entity
@Table(name = "orden_produccion")
public class OrdenProduccionEntidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_orden")
	private Integer idOrden;
	@Column(name = "id_producto")
	private Integer idProducto;
	@Column(name = "fecha_programada")
	private LocalDate fechaProgramada;
	@Column(name = "cantidad_programada")
	private BigDecimal cantidadProgramada;
	@Column(name = "estado")
	private String estado;
	@Column(name = "responsable")
	private String responsable;
}
