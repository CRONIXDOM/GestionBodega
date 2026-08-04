package com.bodega.control.dominio.entidades;

public class Tipo {

	private Integer idTipo;
	private String descripcion;
	private String clase;

	public Tipo() {
		super();
	}

	public Tipo(Integer idTipo, String descripcion, String clase) {
		super();
		this.idTipo = idTipo;
		this.descripcion = descripcion;
		this.clase = clase;
	}

	public Integer getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

}
