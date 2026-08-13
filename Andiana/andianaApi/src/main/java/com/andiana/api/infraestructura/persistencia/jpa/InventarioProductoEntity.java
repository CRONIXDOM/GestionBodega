package com.andiana.api.infraestructura.persistencia.jpa;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "inventario_producto")
public class InventarioProductoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_inventario")
	private Integer idInventario;
	@Column(name = "id_lote")
	private Integer idLote;
	@Column(name = "cantidad")
	private BigDecimal cantidad;
	@Column(name = "ubicacion", length = 80)
	private String ubicacion;
	@Column(name = "fecha_ingreso")
	private LocalDate fechaIngreso;
}
