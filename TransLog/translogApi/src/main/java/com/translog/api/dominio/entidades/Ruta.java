package com.translog.api.dominio.entidades;

import java.math.BigDecimal;

public class Ruta {

	private Integer idRuta;
	private Integer idCiudadOrigen;
	private Integer idCiudadDestino;
	private BigDecimal distanciaKm;

	public Ruta() {
	}

	public Ruta(Integer idRuta, Integer idCiudadOrigen, Integer idCiudadDestino, BigDecimal distanciaKm) {
		this.idRuta = idRuta;
		this.idCiudadOrigen = idCiudadOrigen;
		this.idCiudadDestino = idCiudadDestino;
		this.distanciaKm = distanciaKm;
	}

	public Integer getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Integer idRuta) {
		this.idRuta = idRuta;
	}

	public Integer getIdCiudadOrigen() {
		return idCiudadOrigen;
	}

	public void setIdCiudadOrigen(Integer idCiudadOrigen) {
		this.idCiudadOrigen = idCiudadOrigen;
	}

	public Integer getIdCiudadDestino() {
		return idCiudadDestino;
	}

	public void setIdCiudadDestino(Integer idCiudadDestino) {
		this.idCiudadDestino = idCiudadDestino;
	}

	public BigDecimal getDistanciaKm() {
		return distanciaKm;
	}

	public void setDistanciaKm(BigDecimal distanciaKm) {
		this.distanciaKm = distanciaKm;
	}
}
