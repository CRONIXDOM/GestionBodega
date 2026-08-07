package com.andiana.api.aplicacion.servicio;

import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.Validar;
import com.andiana.api.dominio.modelo.OrdenProduccion;
import com.andiana.api.dominio.puerto.LoteProduccionRepositorio;
import com.andiana.api.dominio.puerto.OrdenProduccionRepositorio;
import com.andiana.api.dominio.puerto.ProductoRepositorio;

public class ServicioOrdenProduccion extends ServicioCrud<OrdenProduccion> {

	private final ProductoRepositorio productos;
	private final LoteProduccionRepositorio lotes;

	public ServicioOrdenProduccion(OrdenProduccionRepositorio ordenes, ProductoRepositorio productos,
			LoteProduccionRepositorio lotes) {
		super(ordenes, "Orden de produccion");
		this.productos = productos;
		this.lotes = lotes;
	}

	@Override
	protected void validar(OrdenProduccion orden) {
		orden.setEstado(Validar.normalizar(orden.getEstado()));
		orden.setResponsable(Validar.normalizar(orden.getResponsable()));

		Validar.obligatorio(orden.getIdProducto(), "producto");
		Validar.obligatorio(orden.getFechaProgramada(), "fecha programada");
		Validar.mayorQueCero(orden.getCantidadProgramada(), "cantidad programada");
		if (orden.getEstado() == null) {
			orden.setEstado("PLANIFICADA");
		}
		// los mismos valores que admite el CHECK de la tabla
		Validar.unoDe(orden.getEstado(), "estado", "PLANIFICADA", "EN_PROCESO", "FINALIZADA", "CANCELADA");

		if (productos.buscarPorId(orden.getIdProducto()).isEmpty()) {
			throw new ReglaNegocioException("El producto indicado no existe");
		}

		// cancelar una orden que ya fabrico lotes dejaria esos lotes sin explicacion
		if ("CANCELADA".equals(orden.getEstado()) && orden.getIdOrden() != null
				&& !lotes.buscarPorOrden(orden.getIdOrden()).isEmpty()) {
			throw new ReglaNegocioException("No se puede cancelar la orden: ya tiene lotes fabricados");
		}
	}

	@Override
	public void eliminar(Integer id) {
		if (!lotes.buscarPorOrden(id).isEmpty()) {
			throw new ReglaNegocioException(
					"No se puede eliminar la orden: ya tiene lotes fabricados. Elimina primero los lotes.");
		}
		super.eliminar(id);
	}
}
