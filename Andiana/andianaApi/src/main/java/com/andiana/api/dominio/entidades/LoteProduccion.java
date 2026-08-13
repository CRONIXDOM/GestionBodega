package com.andiana.api.dominio.entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LoteProduccion {

	private Integer idLote;
	private Integer idOrden;
	private String numeroLote;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private BigDecimal cantidadProducida;
	private String estado;
	
	public LoteProduccion() {
		super();
	}
	public LoteProduccion(Integer idLote, Integer idOrden, String numeroLote, LocalDateTime fechaInicio,
			LocalDateTime fechaFin, BigDecimal cantidadProducida, String estado) {
		super();
		this.idLote = idLote;
		this.idOrden = idOrden;
		this.numeroLote = numeroLote;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.cantidadProducida = cantidadProducida;
		this.estado = estado;
	}
	public Integer getIdLote() {
		return idLote;
	}
	public void setIdLote(Integer idLote) {
		this.idLote = idLote;
	}
	public Integer getIdOrden() {
		return idOrden;
	}
	public void setIdOrden(Integer idOrden) {
		this.idOrden = idOrden;
	}
	public String getNumeroLote() {
		return numeroLote;
	}
	public void setNumeroLote(String numeroLote) {
		this.numeroLote = numeroLote;
	}
	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public LocalDateTime getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}
	public BigDecimal getCantidadProducida() {
		return cantidadProducida;
	}
	public void setCantidadProducida(BigDecimal cantidadProducida) {
		this.cantidadProducida = cantidadProducida;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
}
