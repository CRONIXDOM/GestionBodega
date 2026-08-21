package com.translog.api.infraestructura.persistencia.jpa;

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
@Table(name = "envio")
public class EnvioEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_envio")
	private Integer idEnvio;
	@Column(name = "id_ciudad_origen")
	private Integer idCiudadOrigen;
	@Column(name = "id_ciudad_destino")
	private Integer idCiudadDestino;
	@Column(name = "peso")
	private BigDecimal peso;
	@Column(name = "fecha_registro")
	private LocalDate fechaRegistro;
	@Column(name = "valor_declarado")
	private BigDecimal valorDeclarado;
	@Column(name = "estado", length = 20)
	private String estado;
	@Column(name = "id_despacho")
	private Integer idDespacho;
}
