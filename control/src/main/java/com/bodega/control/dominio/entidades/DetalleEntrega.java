package com.bodega.control.dominio.entidades;

public class DetalleEntrega {

	private Integer idDetalleEntrega;
	private String nombreProducto;
	private String cantidadProducto;
	private String codigoEvento;
	private String nombreEvento;
	private Entrega entrega;

	public DetalleEntrega() {
		super();
	}

	public DetalleEntrega(Integer idDetalleEntrega, String nombreProducto, String cantidadProducto, String codigoEvento,
			String nombreEvento, Entrega entrega) {
		super();
		this.idDetalleEntrega = idDetalleEntrega;
		this.nombreProducto = nombreProducto;
		this.cantidadProducto = cantidadProducto;
		this.codigoEvento = codigoEvento;
		this.nombreEvento = nombreEvento;
		this.entrega = entrega;
	}

	public Integer getIdDetalleEntrega() {
		return idDetalleEntrega;
	}

	public void setIdDetalleEntrega(Integer idDetalleEntrega) {
		this.idDetalleEntrega = idDetalleEntrega;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getCantidadProducto() {
		return cantidadProducto;
	}

	public void setCantidadProducto(String cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}

	public String getCodigoEvento() {
		return codigoEvento;
	}

	public void setCodigoEvento(String codigoEvento) {
		this.codigoEvento = codigoEvento;
	}

	public String getNombreEvento() {
		return nombreEvento;
	}

	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}

	public Entrega getEntrega() {
		return entrega;
	}

	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}

}
