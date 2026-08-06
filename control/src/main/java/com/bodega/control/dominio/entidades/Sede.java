package com.bodega.control.dominio.entidades;

public class Sede {

	private Integer idSede;
	private String nombreSede;
	private String direccion;
	private String descripcion;
	private Integer capacidad;

	public Sede() {
		super();
	}

	public Sede(Integer idSede, String nombreSede, String direccion, String descripcion) {
		super();
		this.idSede = idSede;
		this.nombreSede = nombreSede;
		this.direccion = direccion;
		this.descripcion = descripcion;
	}

	public Sede(Integer idSede, String nombreSede, String direccion, String descripcion, Integer capacidad) {
		this(idSede, nombreSede, direccion, descripcion);
		this.capacidad = capacidad;
	}

	public Integer getIdSede() {
		return idSede;
	}

	public void setIdSede(Integer idSede) {
		this.idSede = idSede;
	}

	public String getNombreSede() {
		return nombreSede;
	}

	public void setNombreSede(String nombreSede) {
		this.nombreSede = nombreSede;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

}
