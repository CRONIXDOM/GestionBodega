package com.translog.api.infraestructura.persistencia.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "conductor")
public class ConductorEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_conductor")
	private Integer idConductor;
	@Column(name = "nombre", length = 120)
	private String nombre;
	@Column(name = "licencia", length = 20)
	private String licencia;
	@Column(name = "estado", length = 20)
	private String estado;
}
