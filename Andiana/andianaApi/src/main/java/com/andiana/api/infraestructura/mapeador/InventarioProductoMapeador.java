package com.andiana.api.infraestructura.mapeador;

import com.andiana.api.dominio.modelo.InventarioProducto;
import com.andiana.api.infraestructura.entidad.InventarioProductoEntidad;

/** Traduce entre la tabla y el modelo del dominio, en los dos sentidos. */
public final class InventarioProductoMapeador {

	private InventarioProductoMapeador() {
	}

	public static InventarioProducto aDominio(InventarioProductoEntidad entidad) {
		if (entidad == null) {
			return null;
		}
		InventarioProducto modelo = new InventarioProducto();
		modelo.setIdInventario(entidad.getIdInventario());
		modelo.setIdLote(entidad.getIdLote());
		modelo.setCantidad(entidad.getCantidad());
		modelo.setUbicacion(entidad.getUbicacion());
		modelo.setFechaIngreso(entidad.getFechaIngreso());
		return modelo;
	}

	public static InventarioProductoEntidad aEntidad(InventarioProducto modelo) {
		if (modelo == null) {
			return null;
		}
		InventarioProductoEntidad entidad = new InventarioProductoEntidad();
		entidad.setIdInventario(modelo.getIdInventario());
		entidad.setIdLote(modelo.getIdLote());
		entidad.setCantidad(modelo.getCantidad());
		entidad.setUbicacion(modelo.getUbicacion());
		entidad.setFechaIngreso(modelo.getFechaIngreso());
		return entidad;
	}
}
