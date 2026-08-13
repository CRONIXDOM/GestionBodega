package com.andiana.api.dominio.entidades;

import java.time.LocalDate;

public class RecetaProduccion {

	private Integer idReceta;
	private Integer idProducto;
	private Integer version;
	private LocalDate fechaVigencia;
	private Boolean estado;
	
	public RecetaProduccion() {
		super();
	}
	public RecetaProduccion(Integer idReceta, Integer idProducto, Integer version, LocalDate fechaVigencia,
			Boolean estado) {
		super();
		this.idReceta = idReceta;
		this.idProducto = idProducto;
		this.version = version;
		this.fechaVigencia = fechaVigencia;
		this.estado = estado;
	}
	public Integer getIdReceta() {
		return idReceta;
	}
	public void setIdReceta(Integer idReceta) {
		this.idReceta = idReceta;
	}
	public Integer getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public LocalDate getFechaVigencia() {
		return fechaVigencia;
	}
	public void setFechaVigencia(LocalDate fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
	
}
