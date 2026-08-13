package com.andiana.api.dominio.entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MovimientoMateriaPrima {

	private Integer idMovimiento;
	private Integer idMateria;
	private LocalDateTime fecha;
	private String tipo;
	private BigDecimal cantidad;
	private String observacion;
	
	
	public MovimientoMateriaPrima() {
		super();
	}
	public MovimientoMateriaPrima(Integer idMovimiento, Integer idMateria, LocalDateTime fecha, String tipo,
			BigDecimal cantidad, String observacion) {
		super();
		this.idMovimiento = idMovimiento;
		this.idMateria = idMateria;
		this.fecha = fecha;
		this.tipo = tipo;
		this.cantidad = cantidad;
		this.observacion = observacion;
	}
	public Integer getIdMovimiento() {
		return idMovimiento;
	}
	public void setIdMovimiento(Integer idMovimiento) {
		this.idMovimiento = idMovimiento;
	}
	public Integer getIdMateria() {
		return idMateria;
	}
	public void setIdMateria(Integer idMateria) {
		this.idMateria = idMateria;
	}
	public LocalDateTime getFecha() {
		return fecha;
	}
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public BigDecimal getCantidad() {
		return cantidad;
	}
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	
}
