package com.bodega.control.infraestructura.persistencia.jpa;

import java.time.LocalDate;

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
@Table(name = "registro")
public class RegistroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRegistro;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;
    
	@ManyToOne
	@JoinColumn(name = "LOTE_idLOTE")
	private LoteEntity lote;

	@ManyToOne
	@JoinColumn(name = "TIPO_idTIPO")
	private TipoEntity tipo;

	@ManyToOne
	@JoinColumn(name = "UBICACION_idUBICACION")
	private UbicacionEntity ubicacion;

	@ManyToOne
	@JoinColumn(name = "DETALLENTREGA_idDETALLENTREGA")
	private DetalleEntregaEntity detalleEntrega;

	@ManyToOne
	@JoinColumn(name = "USUARIOROL_idUSUARIOROL")
	private UsuarioRolEntity usuarioRol;

}