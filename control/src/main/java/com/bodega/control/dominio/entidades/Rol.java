package com.bodega.control.dominio.entidades;

public class Rol {
	private Integer idRol;
	private String nombreRol;
	private String descripcionRol;
	private String modulos;

	public Rol() {
	}

	public Rol(Integer idRol, String nombreRol, String descripcionRol) {
		this.idRol = idRol;
		this.nombreRol = nombreRol;
		this.descripcionRol = descripcionRol;
	}

	public Rol(Integer idRol, String nombreRol, String descripcionRol, String modulos) {
		this.idRol = idRol;
		this.nombreRol = nombreRol;
		this.descripcionRol = descripcionRol;
		this.modulos = modulos;
	}

	public Integer getIdRol() {
		return idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	public String getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

	public String getDescripcionRol() {
		return descripcionRol;
	}

	public void setDescripcionRol(String descripcionRol) {
		this.descripcionRol = descripcionRol;
	}

	public String getModulos() {
		return modulos;
	}

	public void setModulos(String modulos) {
		this.modulos = modulos;
	}

}
