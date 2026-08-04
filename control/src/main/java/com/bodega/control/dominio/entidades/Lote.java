package com.bodega.control.dominio.entidades;

import java.time.LocalDate;

public class Lote {

	private Integer idLote;
	private String numeroLote;
	private LocalDate fechaIngreso;
	private LocalDate fechaVencimiento;
	private String cantidadLote;
	private Producto producto;

	public Lote() {
		super();
	}

	public Lote(Integer idLote, String numeroLote, LocalDate fechaIngreso, LocalDate fechaVencimiento,
			String cantidadLote, Producto producto) {
		super();
		this.idLote = idLote;
		this.numeroLote = numeroLote;
		this.fechaIngreso = fechaIngreso;
		this.fechaVencimiento = fechaVencimiento;
		this.cantidadLote = cantidadLote;
		this.producto = producto;
	}

	public Integer getIdLote() {
		return idLote;
	}

	public void setIdLote(Integer idLote) {
		this.idLote = idLote;
	}

	public String getNumeroLote() {
		return numeroLote;
	}

	public void setNumeroLote(String numeroLote) {
		this.numeroLote = numeroLote;
	}

	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDate fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getCantidadLote() {
		return cantidadLote;
	}

	public void setCantidadLote(String cantidadLote) {
		this.cantidadLote = cantidadLote;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}
