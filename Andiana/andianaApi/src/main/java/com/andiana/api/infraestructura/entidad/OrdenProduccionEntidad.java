package com.andiana.api.infraestructura.entidad;

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
	@Column(name = "codigo")
	private String codigo;
	@Column(name = "id_producto")
	private Integer idProducto;
	@Column(name = "cantidad_programada")
	private Integer cantidadProgramada;
	@Column(name = "fecha_produccion")
	private LocalDate fechaProduccion;
	@Column(name = "estado")
	private String estado;
}
