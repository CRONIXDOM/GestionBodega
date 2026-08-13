package com.andiana.api.dominio.entidades;

public class Producto {

	private Integer idProducto;
	private String nombre;
	private String tipo;
	private String presentacion;
	private Integer volumenMl;
	private Boolean estado;
	
	public Producto() {
		super();
	}
	public Producto(Integer idProducto, String nombre, String tipo, String presentacion, Integer volumenMl,
			Boolean estado) {
		super();
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.tipo = tipo;
		this.presentacion = presentacion;
		this.volumenMl = volumenMl;
		this.estado = estado;
	}
	public Integer getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getPresentacion() {
		return presentacion;
	}
	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}
	public Integer getVolumenMl() {
		return volumenMl;
	}
	public void setVolumenMl(Integer volumenMl) {
		this.volumenMl = volumenMl;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
	
}
