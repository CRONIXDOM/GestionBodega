package com.bodega.control.dominio.entidades;

public class Zona {

	private Integer idZona;
	private String nombreZona;
	private String descripcion;
	private String capacidadZona;

	public Zona() {
		super();
	}

	public Zona(Integer idZona, String nombreZona, String descripcion, String capacidadZona) {
		super();
		this.idZona = idZona;
		this.nombreZona = nombreZona;
		this.descripcion = descripcion;
		this.capacidadZona = capacidadZona;
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

	public String getCapacidadZona() {
		return capacidadZona;
	}

	public void setCapacidadZona(String capacidadZona) {
		this.capacidadZona = capacidadZona;
	}

}
