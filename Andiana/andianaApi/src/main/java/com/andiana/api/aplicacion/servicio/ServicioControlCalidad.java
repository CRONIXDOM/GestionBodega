package com.andiana.api.aplicacion.servicio;

import java.time.LocalDateTime;

import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.Validar;
import com.andiana.api.dominio.modelo.ControlCalidad;
import com.andiana.api.dominio.puerto.ControlCalidadRepositorio;
import com.andiana.api.dominio.puerto.InventarioProductoRepositorio;
import com.andiana.api.dominio.puerto.LoteProduccionRepositorio;

public class ServicioControlCalidad extends ServicioCrud<ControlCalidad> {

	public static final String APROBADO = "APROBADO";
	public static final String OBSERVADO = "OBSERVADO";
	public static final String RECHAZADO = "RECHAZADO";

	private final ControlCalidadRepositorio controles;
	private final LoteProduccionRepositorio lotes;
	private final InventarioProductoRepositorio inventario;

	public ServicioControlCalidad(ControlCalidadRepositorio controles, LoteProduccionRepositorio lotes,
			InventarioProductoRepositorio inventario) {
		super(controles, "Control de calidad");
		this.controles = controles;
		this.lotes = lotes;
		this.inventario = inventario;
	}

	@Override
	protected void validar(ControlCalidad control) {
		control.setResultado(Validar.normalizar(control.getResultado()));
		control.setObservaciones(Validar.normalizar(control.getObservaciones()));

		Validar.obligatorio(control.getIdLote(), "lote");
		// los mismos valores que admite el CHECK de la tabla
		Validar.unoDe(control.getResultado(), "resultado", APROBADO, OBSERVADO, RECHAZADO);

		if (control.getFechaControl() == null) {
			control.setFechaControl(LocalDateTime.now());
		}

		// rangos con los que trabaja el laboratorio en bebidas gaseosas
		Validar.enRango(control.getPh(), "pH", 0, 14);
		Validar.enRango(control.getBrix(), "grados Brix", 0, 30);
		Validar.enRango(control.getTemperatura(), "temperatura", -10, 60);

		if (lotes.buscarPorId(control.getIdLote()).isEmpty()) {
			throw new ReglaNegocioException("El lote indicado no existe");
		}

		// si el lote ya se envio al inventario fue porque estaba aprobado: bajarle
		// ahora el resultado dejaria mercaderia no apta guardada como buena
		if (!APROBADO.equals(control.getResultado()) && !inventario.buscarPorLote(control.getIdLote()).isEmpty()) {
			throw new ReglaNegocioException("Ese lote ya esta en el inventario: para cambiar el resultado hay"
					+ " que sacarlo del inventario primero");
		}
	}

	@Override
	public void eliminar(Integer id) {
		ControlCalidad control = buscarPorId(id);
		if (!inventario.buscarPorLote(control.getIdLote()).isEmpty()) {
			throw new ReglaNegocioException("No se puede eliminar el control: el lote ya esta en el inventario");
		}
		super.eliminar(id);
	}

	/**
	 * El resultado que vale para un lote es el de su control mas reciente: el
	 * laboratorio puede volver a inspeccionarlo despues de una observacion.
	 */
	public String resultadoVigente(Integer idLote) {
		return controles.buscarPorLote(idLote).stream()
				.findFirst()
				.map(ControlCalidad::getResultado)
				.orElse(null);
	}
}
