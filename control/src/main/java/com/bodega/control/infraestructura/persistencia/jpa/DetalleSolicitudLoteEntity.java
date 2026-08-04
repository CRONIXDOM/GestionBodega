package com.bodega.control.infraestructura.persistencia.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Registra de que lote(s) se reservo cada Detalle Solicitud (una solicitud puede
 * cubrirse repartida entre varios lotes cuando el mas antiguo no alcanza).
 * Es la base para que, al momento de la Entrega, se sepa exactamente que lote
 * descontar en vez de tener que volver a calcular el FIFO.
 */
@Entity
@Data
@Table(name = "detalle_solicitud_lote")
public class DetalleSolicitudLoteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idDetalleSolicitudLote;

	@ManyToOne
	@JoinColumn(name = "DETALLESOLICITUD_idDETALLESOLICITUD", nullable = false)
	private DetalleSolicitudEntity detalleSolicitud;

	@ManyToOne
	@JoinColumn(name = "LOTE_idLOTE", nullable = false)
	private LoteEntity lote;

	@Column(name = "cantidad", nullable = false)
	private Integer cantidad;

}
