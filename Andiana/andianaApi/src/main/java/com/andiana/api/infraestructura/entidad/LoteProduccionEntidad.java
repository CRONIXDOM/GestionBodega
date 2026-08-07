package com.andiana.api.infraestructura.entidad;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/** Reflejo exacto de la tabla lote_produccion, tal y como ya existe en la base. */
@Data
@Entity
@Table(name = "lote_produccion")
public class LoteProduccionEntidad {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_lote")
	private Integer idLote;
	@Column(name = "codigo_lote")
	private String codigoLote;
	@Column(name = "id_orden")
	private Integer idOrden;
	@Column(name = "cantidad_producida")
	private Integer cantidadProducida;
	@Column(name = "fecha_fabricacion")
	private LocalDate fechaFabricacion;
}
