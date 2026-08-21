package com.translog.api.dominio.entidades;

public class Ciudad {

	private Integer idCiudad;
	private String nombre;

	public Ciudad() {
	}

	public Ciudad(Integer idCiudad, String nombre) {
		this.idCiudad = idCiudad;
		this.nombre = nombre;
	}

	public Integer getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(Integer idCiudad) {
		this.idCiudad = idCiudad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
