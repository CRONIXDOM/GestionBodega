package com.andiana.api.dominio.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InventarioProducto {

	private Integer idInventario;
	private Integer idLote;
	private BigDecimal cantidad;
	private String ubicacion;
	private LocalDate fechaIngreso;	
	
	public InventarioProducto() {
		super();
	}
	public InventarioProducto(Integer idInventario, Integer idLote, BigDecimal cantidad, String ubicacion,
			LocalDate fechaIngreso) {
		super();
		this.idInventario = idInventario;
		this.idLote = idLote;
		this.cantidad = cantidad;
		this.ubicacion = ubicacion;
		this.fechaIngreso = fechaIngreso;
	}
	public Integer getIdInventario() {
		return idInventario;
	}
	public void setIdInventario(Integer idInventario) {
		this.idInventario = idInventario;
	}
	public Integer getIdLote() {
		return idLote;
	}
	public void setIdLote(Integer idLote) {
		this.idLote = idLote;
	}
	public BigDecimal getCantidad() {
		return cantidad;
	}
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	
	
}
