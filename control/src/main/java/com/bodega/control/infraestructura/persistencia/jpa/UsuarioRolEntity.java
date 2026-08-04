package com.bodega.control.infraestructura.persistencia.jpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "usuario_rol")
public class UsuarioRolEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUsuarioRol;

	@Column(name = "fecha_asignacion")
	private LocalDate fechaAsignacion;

	@ManyToOne
	@JoinColumn(name = "USUARIO_idUSUARIO", nullable = false)
	private UsuarioEntity usuario;

	@ManyToOne
	@JoinColumn(name = "ROL_idROL", nullable = false)
	private RolEntity rol;

	@OneToMany(mappedBy = "usuarioRol")
	private List<RegistroEntity> registros = new ArrayList<>();

	@OneToMany(mappedBy = "usuarioRol")
	private List<SolicitudEntity> solicitudes = new ArrayList<>();


}