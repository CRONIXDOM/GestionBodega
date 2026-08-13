package com.andiana.api.dominio.entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ControlCalidad {

	private Integer idControl;
	private Integer idLote;
	private LocalDateTime fechaControl;
	private BigDecimal ph;
	private BigDecimal brix;
	private BigDecimal temperatura;
	private String resultado;
	private String observaciones;
	
	public ControlCalidad() {
		super();
	}
	public ControlCalidad(Integer idControl, Integer idLote, LocalDateTime fechaControl, BigDecimal ph, BigDecimal brix,
			BigDecimal temperatura, String resultado, String observaciones) {
		super();
		this.idControl = idControl;
		this.idLote = idLote;
		this.fechaControl = fechaControl;
		this.ph = ph;
		this.brix = brix;
		this.temperatura = temperatura;
		this.resultado = resultado;
		this.observaciones = observaciones;
	}
	public Integer getIdControl() {
		return idControl;
	}
	public void setIdControl(Integer idControl) {
		this.idControl = idControl;
	}
	public Integer getIdLote() {
		return idLote;
	}
	public void setIdLote(Integer idLote) {
		this.idLote = idLote;
	}
	public LocalDateTime getFechaControl() {
		return fechaControl;
	}
	public void setFechaControl(LocalDateTime fechaControl) {
		this.fechaControl = fechaControl;
	}
	public BigDecimal getPh() {
		return ph;
	}
	public void setPh(BigDecimal ph) {
		this.ph = ph;
	}
	public BigDecimal getBrix() {
		return brix;
	}
	public void setBrix(BigDecimal brix) {
		this.brix = brix;
	}
	public BigDecimal getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(BigDecimal temperatura) {
		this.temperatura = temperatura;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	
}
