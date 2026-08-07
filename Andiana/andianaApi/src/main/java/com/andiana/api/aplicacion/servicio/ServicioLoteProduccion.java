package com.andiana.api.aplicacion.servicio;

import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.Validar;
import com.andiana.api.dominio.modelo.LoteProduccion;
import com.andiana.api.dominio.puerto.ControlCalidadRepositorio;
import com.andiana.api.dominio.puerto.InventarioProductoRepositorio;
import com.andiana.api.dominio.puerto.LoteProduccionRepositorio;
import com.andiana.api.dominio.puerto.OrdenProduccionRepositorio;

public class ServicioLoteProduccion extends ServicioCrud<LoteProduccion> {

	private final LoteProduccionRepositorio lotes;
	private final OrdenProduccionRepositorio ordenes;
	private final ControlCalidadRepositorio controles;
	private final InventarioProductoRepositorio inventario;

	public ServicioLoteProduccion(LoteProduccionRepositorio lotes, OrdenProduccionRepositorio ordenes,
			ControlCalidadRepositorio controles, InventarioProductoRepositorio inventario) {
		super(lotes, "Lote de produccion");
		this.lotes = lotes;
		this.ordenes = ordenes;
		this.controles = controles;
		this.inventario = inventario;
	}

	@Override
	protected void validar(LoteProduccion lote) {
		lote.setNumeroLote(Validar.normalizar(lote.getNumeroLote()));
		lote.setEstado(Validar.normalizar(lote.getEstado()));

		Validar.obligatorio(lote.getNumeroLote(), "numero de lote");
		Validar.obligatorio(lote.getIdOrden(), "orden de produccion");
		if (lote.getEstado() == null) {
			lote.setEstado("EN_PROCESO");
		}
		// los mismos valores que admite el CHECK de la tabla
		Validar.unoDe(lote.getEstado(), "estado", "EN_PROCESO", "FINALIZADO", "RECHAZADO");

		if (ordenes.buscarPorId(lote.getIdOrden()).isEmpty()) {
			throw new ReglaNegocioException("La orden de produccion indicada no existe");
		}
		Validar.noRepetido(lotes.listar(), LoteProduccion::getIdLote, LoteProduccion::getNumeroLote,
				lote.getIdLote(), lote.getNumeroLote(), "un lote con el numero");

		if (lote.getFechaInicio() != null && lote.getFechaFin() != null
				&& lote.getFechaFin().isBefore(lote.getFechaInicio())) {
			throw new ReglaNegocioException("La fecha de fin no puede ser anterior a la de inicio");
		}

		// un lote terminado tiene que decir cuanto produjo, o no se sabria cuanto
		// puede entrar al almacen
		if ("FINALIZADO".equals(lote.getEstado())) {
			Validar.mayorQueCero(lote.getCantidadProducida(), "cantidad producida");
		} else if (lote.getCantidadProducida() != null && lote.getCantidadProducida().signum() < 0) {
			throw new ReglaNegocioException("La cantidad producida no puede ser negativa");
		}
	}

	@Override
	public void eliminar(Integer id) {
		if (!inventario.buscarPorLote(id).isEmpty()) {
			throw new ReglaNegocioException("No se puede eliminar el lote: ya esta guardado en el inventario");
		}
		if (!controles.buscarPorLote(id).isEmpty()) {
			throw new ReglaNegocioException("No se puede eliminar el lote: ya tiene control de calidad");
		}
		super.eliminar(id);
	}
}
