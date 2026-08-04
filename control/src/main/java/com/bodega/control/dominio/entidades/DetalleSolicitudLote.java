package com.bodega.control.dominio.entidades;

public class DetalleSolicitudLote {

	private Integer idDetalleSolicitudLote;
	private DetalleSolicitud detalleSolicitud;
	private Lote lote;
	private Integer cantidad;

	public DetalleSolicitudLote() {
		super();
	}

	public DetalleSolicitudLote(Integer idDetalleSolicitudLote, DetalleSolicitud detalleSolicitud, Lote lote,
			Integer cantidad) {
		super();
		this.idDetalleSolicitudLote = idDetalleSolicitudLote;
		this.detalleSolicitud = detalleSolicitud;
		this.lote = lote;
		this.cantidad = cantidad;
	}

	public Integer getIdDetalleSolicitudLote() {
		return idDetalleSolicitudLote;
	}

	public void setIdDetalleSolicitudLote(Integer idDetalleSolicitudLote) {
		this.idDetalleSolicitudLote = idDetalleSolicitudLote;
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

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

}
