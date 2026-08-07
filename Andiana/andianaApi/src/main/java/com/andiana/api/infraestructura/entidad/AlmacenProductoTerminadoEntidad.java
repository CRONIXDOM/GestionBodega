package com.andiana.api.infraestructura.entidad;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/** Reflejo exacto de la tabla almacen_producto_terminado, tal y como ya existe en la base. */
@Data
@Entity
@Table(name = "almacen_producto_terminado")
public class AlmacenProductoTerminadoEntidad {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_almacen")
	private Integer idAlmacen;
	@Column(name = "id_lote")
	private Integer idLote;
	@Column(name = "cantidad")
	private Integer cantidad;
	@Column(name = "ubicacion_fisica")
	private String ubicacionFisica;
	@Column(name = "fecha_ingreso")
	private LocalDate fechaIngreso;
}
