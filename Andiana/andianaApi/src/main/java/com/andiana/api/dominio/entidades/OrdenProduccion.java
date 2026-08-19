package com.andiana.api.dominio.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrdenProduccion {

	private Integer idOrden;
	private Integer idProducto;
	private LocalDate fechaProgramada;
	private BigDecimal cantidadProgramada;
	private String estado;
	private String responsable;
	
	
	public OrdenProduccion() {
		super();
	}
	public OrdenProduccion(Integer idOrden, Integer idProducto, LocalDate fechaProgramada,
			BigDecimal cantidadProgramada, String estado, String responsable) {
		super();
		this.idOrden = idOrden;
		this.idProducto = idProducto;
		this.fechaProgramada = fechaProgramada;
		this.cantidadProgramada = cantidadProgramada;
		this.estado = estado;
		this.responsable = responsable;
	}
	public Integer getIdOrden() {
		return idOrden;
	}
	public void setIdOrden(Integer idOrden) {
		this.idOrden = idOrden;
	}
	public Integer getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}
	public LocalDate getFechaProgramada() {
		return fechaProgramada;
	}
	public void setFechaProgramada(LocalDate fechaProgramada) {
		this.fechaProgramada = fechaProgramada;
	}
	public BigDecimal getCantidadProgramada() {
		return cantidadProgramada;
	}
	public void setCantidadProgramada(BigDecimal cantidadProgramada) {
		this.cantidadProgramada = cantidadProgramada;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getResponsable() {
		return responsable;
	}
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	
	
}
