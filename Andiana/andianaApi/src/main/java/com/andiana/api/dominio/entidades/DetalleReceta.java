package com.andiana.api.dominio.entidades;

import java.math.BigDecimal;

public class DetalleReceta {

	private Integer idDetalle;
	private Integer idReceta;
	private Integer idMateria;
	private BigDecimal cantidad;
	private String unidad;
	
	public DetalleReceta() {
		super();
	}
	public DetalleReceta(Integer idDetalle, Integer idReceta, Integer idMateria, BigDecimal cantidad, String unidad) {
		super();
		this.idDetalle = idDetalle;
		this.idReceta = idReceta;
		this.idMateria = idMateria;
		this.cantidad = cantidad;
		this.unidad = unidad;
	}
	public Integer getIdDetalle() {
		return idDetalle;
	}
	public void setIdDetalle(Integer idDetalle) {
		this.idDetalle = idDetalle;
	}
	public Integer getIdReceta() {
		return idReceta;
	}
	public void setIdReceta(Integer idReceta) {
		this.idReceta = idReceta;
	}
	public Integer getIdMateria() {
		return idMateria;
	}
	public void setIdMateria(Integer idMateria) {
		this.idMateria = idMateria;
	}
	public BigDecimal getCantidad() {
		return cantidad;
	}
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
	public String getUnidad() {
		return unidad;
	}
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	
	
}
