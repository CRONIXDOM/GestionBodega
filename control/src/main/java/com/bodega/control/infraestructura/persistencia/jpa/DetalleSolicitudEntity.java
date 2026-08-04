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
@Table(name = "detalle_solicitud")
public class DetalleSolicitudEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idDetalleSolicitud;

	@Column(name = "cantidad_producto")
	private Integer cantidadProducto;

	@Column(name = "lugar_recogida", length = 100)
	private String lugarRecogida;

	@ManyToOne
	@JoinColumn(name = "PRODUCTO_idPRODUCTO", nullable = false)
	private ProductoEntity producto;
}