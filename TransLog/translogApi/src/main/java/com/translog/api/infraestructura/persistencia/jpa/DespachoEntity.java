package com.translog.api.infraestructura.persistencia.jpa;

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
@Table(name = "despacho")
public class DespachoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_despacho")
	private Integer idDespacho;
	@Column(name = "fecha_despacho")
	private LocalDate fechaDespacho;
	@Column(name = "id_ruta")
	private Integer idRuta;
	@Column(name = "id_vehiculo")
	private Integer idVehiculo;
	@Column(name = "id_conductor")
	private Integer idConductor;
	@Column(name = "estado", length = 20)
	private String estado;
}
