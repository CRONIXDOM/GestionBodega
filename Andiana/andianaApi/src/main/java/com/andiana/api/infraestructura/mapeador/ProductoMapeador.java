package com.andiana.api.infraestructura.mapeador;

import com.andiana.api.dominio.modelo.Producto;
import com.andiana.api.infraestructura.entidad.ProductoEntidad;

/** Traduce entre la tabla y el modelo del dominio, en los dos sentidos. */
public final class ProductoMapeador {

	private ProductoMapeador() {
	}

	public static Producto aDominio(ProductoEntidad entidad) {
		if (entidad == null) {
			return null;
		}
		Producto modelo = new Producto();
		modelo.setIdProducto(entidad.getIdProducto());
		modelo.setNombre(entidad.getNombre());
		modelo.setTipo(entidad.getTipo());
		modelo.setPresentacion(entidad.getPresentacion());
		modelo.setVolumenMl(entidad.getVolumenMl());
		modelo.setEstado(entidad.getEstado());
		return modelo;
	}

	public static ProductoEntidad aEntidad(Producto modelo) {
		if (modelo == null) {
			return null;
		}
		ProductoEntidad entidad = new ProductoEntidad();
		entidad.setIdProducto(modelo.getIdProducto());
		entidad.setNombre(modelo.getNombre());
		entidad.setTipo(modelo.getTipo());
		entidad.setPresentacion(modelo.getPresentacion());
		entidad.setVolumenMl(modelo.getVolumenMl());
		entidad.setEstado(modelo.getEstado());
		return entidad;
	}
}
