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

/** Reflejo exacto de la tabla inventario_producto, tal y como ya existe en la base. */
@Data
@Entity
@Table(name = "inventario_producto")
public class InventarioProductoEntidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_inventario")
	private Integer idInventario;
	@Column(name = "id_lote")
	private Integer idLote;
	@Column(name = "cantidad")
	private BigDecimal cantidad;
	@Column(name = "ubicacion")
	private String ubicacion;
	@Column(name = "fecha_ingreso")
	private LocalDate fechaIngreso;
}
