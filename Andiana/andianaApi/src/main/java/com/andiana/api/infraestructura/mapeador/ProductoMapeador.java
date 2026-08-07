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
		modelo.setPresentacion(entidad.getPresentacion());
		return modelo;
	}

	public static ProductoEntidad aEntidad(Producto modelo) {
		if (modelo == null) {
			return null;
		}
		ProductoEntidad entidad = new ProductoEntidad();
		entidad.setIdProducto(modelo.getIdProducto());
		entidad.setNombre(modelo.getNombre());
		entidad.setPresentacion(modelo.getPresentacion());
		return entidad;
	}
}
