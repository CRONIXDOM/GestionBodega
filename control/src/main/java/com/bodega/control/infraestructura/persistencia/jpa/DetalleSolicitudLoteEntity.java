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
