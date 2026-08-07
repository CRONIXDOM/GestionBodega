package com.andiana.api.aplicacion.servicio;

import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.Validar;
import com.andiana.api.dominio.modelo.LoteProduccion;
import com.andiana.api.dominio.puerto.AlmacenProductoTerminadoRepositorio;
import com.andiana.api.dominio.puerto.ControlCalidadRepositorio;
import com.andiana.api.dominio.puerto.LoteProduccionRepositorio;
import com.andiana.api.dominio.puerto.OrdenProduccionRepositorio;

public class ServicioLoteProduccion extends ServicioCrud<LoteProduccion> {

	private final LoteProduccionRepositorio lotes;
	private final OrdenProduccionRepositorio ordenes;
	private final ControlCalidadRepositorio controles;
	private final AlmacenProductoTerminadoRepositorio almacen;

	public ServicioLoteProduccion(LoteProduccionRepositorio lotes, OrdenProduccionRepositorio ordenes,
			ControlCalidadRepositorio controles, AlmacenProductoTerminadoRepositorio almacen) {
		super(lotes, "Lote de produccion");
		this.lotes = lotes;
		this.ordenes = ordenes;
		this.controles = controles;
		this.almacen = almacen;
	}

	@Override
	protected void validar(LoteProduccion lote) {
		lote.setCodigoLote(Validar.normalizar(lote.getCodigoLote()));

		Validar.obligatorio(lote.getCodigoLote(), "codigo de lote");
		Validar.obligatorio(lote.getIdOrden(), "orden de produccion");
		Validar.mayorQueCero(lote.getCantidadProducida(), "cantidad producida");
		Validar.obligatorio(lote.getFechaFabricacion(), "fecha de fabricacion");

		if (ordenes.buscarPorId(lote.getIdOrden()).isEmpty()) {
			throw new ReglaNegocioException("La orden de produccion indicada no existe");
		}
		Validar.noRepetido(lotes.listar(), LoteProduccion::getIdLote, LoteProduccion::getCodigoLote,
				lote.getIdLote(), lote.getCodigoLote(), "un lote con el codigo");
	}

	@Override
	public void eliminar(Integer id) {
		if (almacen.buscarPorLote(id).isPresent()) {
			throw new ReglaNegocioException("No se puede eliminar el lote: ya esta guardado en el almacen");
		}
		if (controles.buscarPorLote(id).isPresent()) {
			throw new ReglaNegocioException("No se puede eliminar el lote: ya tiene control de calidad");
		}
		super.eliminar(id);
	}
}
