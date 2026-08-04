package com.bodega.control.dominio.entidades;

import java.time.LocalDate;

public class Ubicacion {

	private Integer idUbicacion;
	private String cantidadUbicacion;
	private LocalDate fechaUbicacion;

	public Ubicacion() {
		super();
	}

	public Ubicacion(Integer idUbicacion, String cantidadUbicacion, LocalDate fechaUbicacion) {
		super();
		this.idUbicacion = idUbicacion;
		this.cantidadUbicacion = cantidadUbicacion;
		this.fechaUbicacion = fechaUbicacion;
	}

	public Integer getIdUbicacion() {
		return idUbicacion;
	}

	public void setIdUbicacion(Integer idUbicacion) {
		this.idUbicacion = idUbicacion;
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

}
