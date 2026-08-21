package com.translog.api.dominio.entidades;

import java.math.BigDecimal;

public class Vehiculo {

	private Integer idVehiculo;
	private String placa;
	private BigDecimal capacidadMaxima;
	private String estado;

	public Vehiculo() {
	}

	public Vehiculo(Integer idVehiculo, String placa, BigDecimal capacidadMaxima, String estado) {
		this.idVehiculo = idVehiculo;
		this.placa = placa;
		this.capacidadMaxima = capacidadMaxima;
		this.estado = estado;
	}

	public Integer getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(Integer idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public BigDecimal getCapacidadMaxima() {
		return capacidadMaxima;
	}

	public void setCapacidadMaxima(BigDecimal capacidadMaxima) {
		this.capacidadMaxima = capacidadMaxima;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
