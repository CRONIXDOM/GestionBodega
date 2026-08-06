package com.bodega.control.dominio.entidades;

public class Zona {

	private Integer idZona;
	private String nombreZona;
	private String descripcion;

	public Zona() {
		super();
	}

	public Zona(Integer idZona, String nombreZona, String descripcion) {
		super();
		this.idZona = idZona;
		this.nombreZona = nombreZona;
		this.descripcion = descripcion;
	}

	public Integer getIdZona() {
		return idZona;
	}

	public void setIdZona(Integer idZona) {
		this.idZona = idZona;
	}

	public String getNombreZona() {
		return nombreZona;
	}

	public void setNombreZona(String nombreZona) {
		this.nombreZona = nombreZona;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
