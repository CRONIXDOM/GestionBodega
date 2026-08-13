package com.andiana.api.dominio.entidades;

import java.math.BigDecimal;

public class MateriaPrima {

	private Integer idMateria;
	private String nombre;
	private String unidadMedida;
	private BigDecimal stockActual;
	private BigDecimal stockMinimo;
	
	public MateriaPrima() {
		super();
	}
	public MateriaPrima(Integer idMateria, String nombre, String unidadMedida, BigDecimal stockActual,
			BigDecimal stockMinimo) {
		super();
		this.idMateria = idMateria;
		this.nombre = nombre;
		this.unidadMedida = unidadMedida;
		this.stockActual = stockActual;
		this.stockMinimo = stockMinimo;
	}
	public Integer getIdMateria() {
		return idMateria;
	}
	public void setIdMateria(Integer idMateria) {
		this.idMateria = idMateria;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getUnidadMedida() {
		return unidadMedida;
	}
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
	public BigDecimal getStockActual() {
		return stockActual;
	}
	public void setStockActual(BigDecimal stockActual) {
		this.stockActual = stockActual;
	}
	public BigDecimal getStockMinimo() {
		return stockMinimo;
	}
	public void setStockMinimo(BigDecimal stockMinimo) {
		this.stockMinimo = stockMinimo;
	}
	
	
}
