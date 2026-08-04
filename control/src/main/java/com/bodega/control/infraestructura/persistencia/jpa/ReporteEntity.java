package com.bodega.control.infraestructura.persistencia.jpa;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "reporte")
public class ReporteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idReporte;

	@Column(name = "tipo_reporte", length = 100)
	private String tipoReporte;

	@Column(name = "fecha_creacion")
	private LocalDate fechaCreacion;

	@ManyToOne
	@JoinColumn(name = "USUARIOROL_idUSUARIOROL")
	private UsuarioRolEntity usuarioRol;

}