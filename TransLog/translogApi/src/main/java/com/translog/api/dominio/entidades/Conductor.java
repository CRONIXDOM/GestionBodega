package com.translog.api.dominio.entidades;

public class Conductor {

	private Integer idConductor;
	private String nombre;
	private String licencia;
	private String estado;

	public Conductor() {
	}

	public Conductor(Integer idConductor, String nombre, String licencia, String estado) {
		this.idConductor = idConductor;
		this.nombre = nombre;
		this.licencia = licencia;
		this.estado = estado;
	}

	public Integer getIdConductor() {
		return idConductor;
	}

	public void setIdConductor(Integer idConductor) {
		this.idConductor = idConductor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLicencia() {
		return licencia;
	}

	public void setLicencia(String licencia) {
		this.licencia = licencia;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
