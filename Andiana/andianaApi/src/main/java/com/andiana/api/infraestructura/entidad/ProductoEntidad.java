package com.andiana.api.infraestructura.entidad;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/** Reflejo exacto de la tabla producto, tal y como ya existe en la base. */
@Data
@Entity
@Table(name = "producto")
public class ProductoEntidad {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_producto")
	private Integer idProducto;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "presentacion")
	private String presentacion;
}
