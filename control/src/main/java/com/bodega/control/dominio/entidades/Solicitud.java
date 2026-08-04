package com.bodega.control.dominio.entidades;

import java.time.LocalDate;

public class Solicitud {

	private Integer idSolicitud;
	private LocalDate fechaSolicitud;
	private Boolean estadoSolicitud;
	private UsuarioRol usuarioRol;

	public Solicitud() {
		super();
	}

	public Solicitud(Integer idSolicitud, LocalDate fechaSolicitud, Boolean estadoSolicitud, UsuarioRol usuarioRol) {
		super();
		this.idSolicitud = idSolicitud;
		this.fechaSolicitud = fechaSolicitud;
		this.estadoSolicitud = estadoSolicitud;
		this.usuarioRol = usuarioRol;
	}

	public Integer getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Integer idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public LocalDate getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(LocalDate fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Boolean getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(Boolean estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public UsuarioRol getUsuarioRol() {
		return usuarioRol;
	}

	public void setUsuarioRol(UsuarioRol usuarioRol) {
		this.usuarioRol = usuarioRol;
	}

}
