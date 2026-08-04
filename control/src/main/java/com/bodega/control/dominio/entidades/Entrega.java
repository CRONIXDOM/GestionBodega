package com.bodega.control.dominio.entidades;

import java.time.LocalDate;

public class Entrega {

	private Integer idEntrega;
	private LocalDate fechaEntrega;
	private String responsableEntrega;

	public Entrega() {
	}

	public Entrega(Integer idEntrega, LocalDate fechaEntrega, String responsableEntrega) {
		this.idEntrega = idEntrega;
		this.fechaEntrega = fechaEntrega;
		this.responsableEntrega = responsableEntrega;
	}

	public Integer getIdEntrega() {
		return idEntrega;
	}

	public void setIdEntrega(Integer idEntrega) {
		this.idEntrega = idEntrega;
	}

	public LocalDate getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(LocalDate fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getResponsableEntrega() {
		return responsableEntrega;
	}

	public void setResponsableEntrega(String responsableEntrega) {
		this.responsableEntrega = responsableEntrega;
	}

}
