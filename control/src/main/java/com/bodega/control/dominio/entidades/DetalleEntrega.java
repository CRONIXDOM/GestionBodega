package com.bodega.control.dominio.entidades;

public class DetalleEntrega {

	private Integer idDetalleEntrega;
	private Producto producto;
	private Integer cantidadProducto;
	private String codigoEvento;
	private String nombreEvento;
	private Entrega entrega;
	private DetalleSolicitud detalleSolicitud;
	private Lote lote;

	public DetalleEntrega() {
		super();
	}

	public DetalleEntrega(Integer idDetalleEntrega, Producto producto, Integer cantidadProducto, String codigoEvento,
			String nombreEvento, Entrega entrega, DetalleSolicitud detalleSolicitud, Lote lote) {
		super();
		this.idDetalleEntrega = idDetalleEntrega;
		this.producto = producto;
		this.cantidadProducto = cantidadProducto;
		this.codigoEvento = codigoEvento;
		this.nombreEvento = nombreEvento;
		this.entrega = entrega;
		this.detalleSolicitud = detalleSolicitud;
		this.lote = lote;
	}

	public Integer getIdDetalleEntrega() {
		return idDetalleEntrega;
	}

	public void setIdDetalleEntrega(Integer idDetalleEntrega) {
		this.idDetalleEntrega = idDetalleEntrega;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Integer getCantidadProducto() {
		return cantidadProducto;
	}

	public void setCantidadProducto(Integer cantidadProducto) {
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

	public DetalleSolicitud getDetalleSolicitud() {
		return detalleSolicitud;
	}

	public void setDetalleSolicitud(DetalleSolicitud detalleSolicitud) {
		this.detalleSolicitud = detalleSolicitud;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

}
