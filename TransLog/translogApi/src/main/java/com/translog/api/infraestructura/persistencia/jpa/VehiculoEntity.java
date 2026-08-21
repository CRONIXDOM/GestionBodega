package com.translog.api.infraestructura.persistencia.jpa;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "vehiculo")
public class VehiculoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_vehiculo")
	private Integer idVehiculo;
	@Column(name = "placa", length = 15)
	private String placa;
	@Column(name = "capacidad_maxima")
	private BigDecimal capacidadMaxima;
	@Column(name = "estado", length = 20)
	private String estado;
}
