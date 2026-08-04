package com.bodega.control.dominio.entidades;

import java.time.LocalDate;

public class Ubicacion {

	private Integer idUbicacion;
	private String codigoUbicacion;
	private String cantidadUbicacion;
	private LocalDate fechaUbicacion;
	private Zona zona;
	private Sede sede;

	public Ubicacion() {
		super();
	}

	public Ubicacion(Integer idUbicacion, String codigoUbicacion, String cantidadUbicacion, LocalDate fechaUbicacion,
			Zona zona) {
		super();
		this.idUbicacion = idUbicacion;
		this.codigoUbicacion = codigoUbicacion;
		this.cantidadUbicacion = cantidadUbicacion;
		this.fechaUbicacion = fechaUbicacion;
		this.zona = zona;
	}

	public Integer getIdUbicacion() {
		return idUbicacion;
	}

	public void setIdUbicacion(Integer idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	public String getCodigoUbicacion() {
		return codigoUbicacion;
	}

	public void setCodigoUbicacion(String codigoUbicacion) {
		this.codigoUbicacion = codigoUbicacion;
	}

	public String getCantidadUbicacion() {
		return cantidadUbicacion;
	}

	public void setCantidadUbicacion(String cantidadUbicacion) {
		this.cantidadUbicacion = cantidadUbicacion;
	}

	public LocalDate getFechaUbicacion() {
		return fechaUbicacion;
	}

	public void setFechaUbicacion(LocalDate fechaUbicacion) {
		this.fechaUbicacion = fechaUbicacion;
	}

	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}

	public Sede getSede() {
		return sede;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}

}
