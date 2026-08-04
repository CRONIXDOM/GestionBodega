package com.bodega.control.dominio.entidades;

import java.time.LocalDate;

public class UsuarioRol {

	private Integer idUsuarioRol;
	private LocalDate fechaAsignacion;
	private Usuario usuario;
	private Rol rol;

	public UsuarioRol() {
		super();
	}

	public UsuarioRol(Integer idUsuarioRol, LocalDate fechaAsignacion, Usuario usuario, Rol rol) {
		super();
		this.idUsuarioRol = idUsuarioRol;
		this.fechaAsignacion = fechaAsignacion;
		this.usuario = usuario;
		this.rol = rol;
	}

	public Integer getIdUsuarioRol() {
		return idUsuarioRol;
	}

	public void setIdUsuarioRol(Integer idUsuarioRol) {
		this.idUsuarioRol = idUsuarioRol;
	}

	public LocalDate getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(LocalDate fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

}
