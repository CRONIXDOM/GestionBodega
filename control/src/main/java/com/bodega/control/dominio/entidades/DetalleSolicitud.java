package com.bodega.control.dominio.entidades;


public class DetalleSolicitud {
	
	private Integer idDetalleSolicitud;
	private Integer cantidadProducto;
	private String lugarRecogida;
	private Producto producto;

	public DetalleSolicitud() {
		super();
	}
	public DetalleSolicitud(Integer idDetalleSolicitud, Integer cantidadProducto,
			String lugarRecogida, Producto producto) {
		super();
		this.idDetalleSolicitud = idDetalleSolicitud;
		this.cantidadProducto = cantidadProducto;
		this.lugarRecogida = lugarRecogida;
		this.producto = producto;
	}
	public Integer getIdDetalleSolicitud() {
		return idDetalleSolicitud;
	}
	public void setIdDetalleSolicitud(Integer idDetalleSolicitud) {
		this.idDetalleSolicitud = idDetalleSolicitud;
	}
	public Integer getCantidadProducto() {
		return cantidadProducto;
	}
	public void setCantidadProducto(Integer cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}
	public String getLugarRecogida() {
		return lugarRecogida;
	}
	public void setLugarRecogida(String lugarRecogida) {
		this.lugarRecogida = lugarRecogida;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}
