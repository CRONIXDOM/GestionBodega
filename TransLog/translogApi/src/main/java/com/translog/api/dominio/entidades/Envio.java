package com.translog.api.dominio.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Envio {

	private Integer idEnvio;
	private Integer idCiudadOrigen;
	private Integer idCiudadDestino;
	private BigDecimal peso;
	private LocalDate fechaRegistro;
	private BigDecimal valorDeclarado;
	private String estado;
	private Integer idDespacho;

	public Envio() {
	}

	public Envio(Integer idEnvio, Integer idCiudadOrigen, Integer idCiudadDestino, BigDecimal peso, LocalDate fechaRegistro, BigDecimal valorDeclarado, String estado, Integer idDespacho) {
		this.idEnvio = idEnvio;
		this.idCiudadOrigen = idCiudadOrigen;
		this.idCiudadDestino = idCiudadDestino;
		this.peso = peso;
		this.fechaRegistro = fechaRegistro;
		this.valorDeclarado = valorDeclarado;
		this.estado = estado;
		this.idDespacho = idDespacho;
	}

	public Integer getIdEnvio() {
		return idEnvio;
	}

	public void setIdEnvio(Integer idEnvio) {
		this.idEnvio = idEnvio;
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

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public BigDecimal getValorDeclarado() {
		return valorDeclarado;
	}

	public void setValorDeclarado(BigDecimal valorDeclarado) {
		this.valorDeclarado = valorDeclarado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getIdDespacho() {
		return idDespacho;
	}

	public void setIdDespacho(Integer idDespacho) {
		this.idDespacho = idDespacho;
	}
}
