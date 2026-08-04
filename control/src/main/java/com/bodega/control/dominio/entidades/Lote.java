package com.bodega.control.dominio.entidades;

import java.time.LocalDate;

public class Lote {

	private Integer idLote;
	private String numeroLote;
	private LocalDate fechaIngreso;
	private LocalDate fechaVencimiento;
	private Integer cantidadLote;
	private Integer cantidadReservada;
	private Producto producto;
	private Ubicacion ubicacion;

	public Lote() {
		super();
	}

	public Lote(Integer idLote, String numeroLote, LocalDate fechaIngreso, LocalDate fechaVencimiento,
			Integer cantidadLote, Integer cantidadReservada, Producto producto, Ubicacion ubicacion) {
		super();
		this.idLote = idLote;
		this.numeroLote = numeroLote;
		this.fechaIngreso = fechaIngreso;
		this.fechaVencimiento = fechaVencimiento;
		this.cantidadLote = cantidadLote;
		this.cantidadReservada = cantidadReservada;
		this.producto = producto;
		this.ubicacion = ubicacion;
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

	public Integer getCantidadLote() {
		return cantidadLote;
	}

	public void setCantidadLote(Integer cantidadLote) {
		this.cantidadLote = cantidadLote;
	}

	public Integer getCantidadReservada() {
		return cantidadReservada;
	}

	public void setCantidadReservada(Integer cantidadReservada) {
		this.cantidadReservada = cantidadReservada;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	public int getCantidadDisponible() {
		int reservada = cantidadReservada == null ? 0 : cantidadReservada;
		int total = cantidadLote == null ? 0 : cantidadLote;
		return total - reservada;
	}

}
