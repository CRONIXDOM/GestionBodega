package com.bodega.control.dominio.entidades;

import java.time.LocalDate;

public class Registro {

	private Integer idRegistro;
	private LocalDate fechaRegistro;
	private Lote lote;
	private Tipo tipo;
	private Ubicacion ubicacion;
	private DetalleEntrega detalleEntrega;
	private UsuarioRol usuarioRol;

	public Registro() {
		super();
	}

	public Registro(Integer idRegistro, LocalDate fechaRegistro, Lote lote, Tipo tipo, Ubicacion ubicacion,
			DetalleEntrega detalleEntrega, UsuarioRol usuarioRol) {
		super();
		this.idRegistro = idRegistro;
		this.fechaRegistro = fechaRegistro;
		this.lote = lote;
		this.tipo = tipo;
		this.ubicacion = ubicacion;
		this.detalleEntrega = detalleEntrega;
		this.usuarioRol = usuarioRol;
	}

	public Integer getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(Integer idRegistro) {
		this.idRegistro = idRegistro;
	}

	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	public DetalleEntrega getDetalleEntrega() {
		return detalleEntrega;
	}

	public void setDetalleEntrega(DetalleEntrega detalleEntrega) {
		this.detalleEntrega = detalleEntrega;
	}

	public UsuarioRol getUsuarioRol() {
		return usuarioRol;
	}

	public void setUsuarioRol(UsuarioRol usuarioRol) {
		this.usuarioRol = usuarioRol;
	}

}
