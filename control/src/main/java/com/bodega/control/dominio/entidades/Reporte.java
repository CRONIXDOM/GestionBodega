package com.bodega.control.dominio.entidades;

import java.time.LocalDate;

public class Reporte {

	private Integer idReporte;
	private String tipoReporte;
	private LocalDate fechaCreacion;
	private UsuarioRol usuarioRol;

	public Reporte() {
		super();
	}

	public Reporte(Integer idReporte, String tipoReporte, LocalDate fechaCreacion, UsuarioRol usuarioRol) {
		super();
		this.idReporte = idReporte;
		this.tipoReporte = tipoReporte;
		this.fechaCreacion = fechaCreacion;
		this.usuarioRol = usuarioRol;
	}

	public Integer getIdReporte() {
		return idReporte;
	}

	public void setIdReporte(Integer idReporte) {
		this.idReporte = idReporte;
	}

	public String getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(String tipoReporte) {
		this.tipoReporte = tipoReporte;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public UsuarioRol getUsuarioRol() {
		return usuarioRol;
	}

	public void setUsuarioRol(UsuarioRol usuarioRol) {
		this.usuarioRol = usuarioRol;
	}

}
