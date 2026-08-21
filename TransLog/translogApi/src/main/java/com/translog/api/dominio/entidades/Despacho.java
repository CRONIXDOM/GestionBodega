package com.translog.api.dominio.entidades;

import java.time.LocalDate;

public class Despacho {

	private Integer idDespacho;
	private LocalDate fechaDespacho;
	private Integer idRuta;
	private Integer idVehiculo;
	private Integer idConductor;
	private String estado;

	public Despacho() {
	}

	public Despacho(Integer idDespacho, LocalDate fechaDespacho, Integer idRuta, Integer idVehiculo, Integer idConductor, String estado) {
		this.idDespacho = idDespacho;
		this.fechaDespacho = fechaDespacho;
		this.idRuta = idRuta;
		this.idVehiculo = idVehiculo;
		this.idConductor = idConductor;
		this.estado = estado;
	}

	public Integer getIdDespacho() {
		return idDespacho;
	}

	public void setIdDespacho(Integer idDespacho) {
		this.idDespacho = idDespacho;
	}

	public LocalDate getFechaDespacho() {
		return fechaDespacho;
	}

	public void setFechaDespacho(LocalDate fechaDespacho) {
		this.fechaDespacho = fechaDespacho;
	}

	public Integer getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Integer idRuta) {
		this.idRuta = idRuta;
	}

	public Integer getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(Integer idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public Integer getIdConductor() {
		return idConductor;
	}

	public void setIdConductor(Integer idConductor) {
		this.idConductor = idConductor;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
