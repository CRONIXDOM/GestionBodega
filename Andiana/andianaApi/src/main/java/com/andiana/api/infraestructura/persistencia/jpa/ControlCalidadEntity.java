package com.andiana.api.infraestructura.persistencia.jpa;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/** Reflejo exacto de la tabla control_calidad, tal y como ya existe en la base. */
@Entity
@Data
@Table(name = "control_calidad")
public class ControlCalidadEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_control")
	private Integer idControl;
	@Column(name = "id_lote")
	private Integer idLote;
	@Column(name = "fecha_control")
	private LocalDateTime fechaControl;
	@Column(name = "ph")
	private BigDecimal ph;
	@Column(name = "brix")
	private BigDecimal brix;
	@Column(name = "temperatura")
	private BigDecimal temperatura;
	@Column(name = "resultado", length = 20)
	private String resultado;
	@Column(name = "observaciones")
	private String observaciones;
}
