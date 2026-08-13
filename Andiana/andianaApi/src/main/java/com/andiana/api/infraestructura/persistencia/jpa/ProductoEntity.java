package com.andiana.api.infraestructura.persistencia.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "producto")
public class ProductoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_producto")
	private Integer idProducto;
	@Column(name = "nombre", length = 120)
	private String nombre;
	@Column(name = "tipo", length = 50)
	private String tipo;
	@Column(name = "presentacion", length = 50)
	private String presentacion;
	@Column(name = "volumen_ml")
	private Integer volumenMl;
	@Column(name = "estado")
	private Boolean estado;
}
