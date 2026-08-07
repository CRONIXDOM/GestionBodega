package com.andiana.api.aplicacion.servicio;

import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.Validar;
import com.andiana.api.dominio.modelo.OrdenProduccion;
import com.andiana.api.dominio.puerto.LoteProduccionRepositorio;
import com.andiana.api.dominio.puerto.OrdenProduccionRepositorio;
import com.andiana.api.dominio.puerto.ProductoRepositorio;

public class ServicioOrdenProduccion extends ServicioCrud<OrdenProduccion> {

	private final OrdenProduccionRepositorio ordenes;
	private final ProductoRepositorio productos;
	private final LoteProduccionRepositorio lotes;

	public ServicioOrdenProduccion(OrdenProduccionRepositorio ordenes, ProductoRepositorio productos,
			LoteProduccionRepositorio lotes) {
		super(ordenes, "Orden de produccion");
		this.ordenes = ordenes;
		this.productos = productos;
		this.lotes = lotes;
	}

	@Override
	protected void validar(OrdenProduccion orden) {
		orden.setCodigo(Validar.normalizar(orden.getCodigo()));
		orden.setEstado(Validar.normalizar(orden.getEstado()));

		Validar.obligatorio(orden.getCodigo(), "codigo");
		Validar.obligatorio(orden.getIdProducto(), "producto");
		Validar.mayorQueCero(orden.getCantidadProgramada(), "cantidad programada");
		Validar.obligatorio(orden.getFechaProduccion(), "fecha de produccion");
		Validar.unoDe(orden.getEstado(), "estado", "PLANIFICADA", "EN PROCESO", "FINALIZADA");

		if (productos.buscarPorId(orden.getIdProducto()).isEmpty()) {
			throw new ReglaNegocioException("El producto indicado no existe");
		}
		Validar.noRepetido(ordenes.listar(), OrdenProduccion::getIdOrden, OrdenProduccion::getCodigo,
				orden.getIdOrden(), orden.getCodigo(), "una orden con el codigo");
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
