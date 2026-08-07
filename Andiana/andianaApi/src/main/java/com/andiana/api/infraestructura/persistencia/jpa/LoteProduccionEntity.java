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

/** Reflejo exacto de la tabla lote_produccion, tal y como ya existe en la base. */
@Entity
@Data
@Table(name = "lote_produccion")
public class LoteProduccionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_lote")
	private Integer idLote;
	@Column(name = "id_orden")
	private Integer idOrden;
	@Column(name = "numero_lote", length = 40)
	private String numeroLote;
	@Column(name = "fecha_inicio")
	private LocalDateTime fechaInicio;
	@Column(name = "fecha_fin")
	private LocalDateTime fechaFin;
	@Column(name = "cantidad_producida")
	private BigDecimal cantidadProducida;
	@Column(name = "estado", length = 20)
	private String estado;
}
