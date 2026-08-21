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
@Table(name = "ruta")
public class RutaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ruta")
	private Integer idRuta;
	@Column(name = "id_ciudad_origen")
	private Integer idCiudadOrigen;
	@Column(name = "id_ciudad_destino")
	private Integer idCiudadDestino;
	@Column(name = "distancia_km")
	private BigDecimal distanciaKm;
}
