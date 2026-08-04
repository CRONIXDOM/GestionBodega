package com.bodega.control.dominio.entidades;

public class Producto {

	private Integer idProducto;
	private String nombreProducto;
	private String codigoProducto;
	private String cantidadProducto;

	public Producto() {
		super();
	}

	public Producto(Integer idProducto, String nombreProducto, String codigoProducto, String cantidadProducto) {
		super();
		this.idProducto = idProducto;
		this.nombreProducto = nombreProducto;
		this.codigoProducto = codigoProducto;
		this.cantidadProducto = cantidadProducto;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public String getCantidadProducto() {
		return cantidadProducto;
	}

	public void setCantidadProducto(String cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}
	
}
