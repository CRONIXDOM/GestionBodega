package com.andiana.api.aplicacion.servicio;

import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.Validar;
import com.andiana.api.dominio.modelo.ControlCalidad;
import com.andiana.api.dominio.puerto.AlmacenProductoTerminadoRepositorio;
import com.andiana.api.dominio.puerto.ControlCalidadRepositorio;
import com.andiana.api.dominio.puerto.LoteProduccionRepositorio;

public class ServicioControlCalidad extends ServicioCrud<ControlCalidad> {

	public static final String APROBADO = "APROBADO";
	public static final String OBSERVADO = "OBSERVADO";
	public static final String RECHAZADO = "RECHAZADO";

	private final ControlCalidadRepositorio controles;
	private final LoteProduccionRepositorio lotes;
	private final AlmacenProductoTerminadoRepositorio almacen;

	public ServicioControlCalidad(ControlCalidadRepositorio controles, LoteProduccionRepositorio lotes,
			AlmacenProductoTerminadoRepositorio almacen) {
		super(controles, "Control de calidad");
		this.controles = controles;
		this.lotes = lotes;
		this.almacen = almacen;
	}

	@Override
	protected void validar(ControlCalidad control) {
		control.setResultado(Validar.normalizar(control.getResultado()));

		Validar.obligatorio(control.getIdLote(), "lote");
		Validar.obligatorio(control.getFechaInspeccion(), "fecha de inspeccion");
		Validar.unoDe(control.getResultado(), "resultado", APROBADO, OBSERVADO, RECHAZADO);

		// rangos con los que trabaja el laboratorio en bebidas gaseosas
		Validar.enRango(control.getPh(), "pH", 0, 14);
		Validar.enRango(control.getGradosBrix(), "grados Brix", 0, 30);
		Validar.enRango(control.getTemperatura(), "temperatura", -10, 60);

		if (lotes.buscarPorId(control.getIdLote()).isEmpty()) {
			throw new ReglaNegocioException("El lote indicado no existe");
		}

		// el laboratorio inspecciona el lote una vez; si hay que corregir algo se
		// edita ese control, no se crea otro
		controles.buscarPorLote(control.getIdLote())
				.filter(otro -> !otro.getIdControl().equals(control.getIdControl()))
				.ifPresent(otro -> {
					throw new ReglaNegocioException("Ese lote ya tiene un control de calidad registrado");
				});

		// si el lote ya se envio al almacen fue porque estaba aprobado: cambiarle
		// ahora el resultado dejaria mercaderia no apta guardada como buena
		if (!APROBADO.equals(control.getResultado()) && almacen.buscarPorLote(control.getIdLote()).isPresent()) {
			throw new ReglaNegocioException("Ese lote ya esta en el almacen: para cambiar el resultado hay que"
					+ " sacarlo del almacen primero");
		}
	}

	@Override
	public void eliminar(Integer id) {
		ControlCalidad control = buscarPorId(id);
		if (almacen.buscarPorLote(control.getIdLote()).isPresent()) {
			throw new ReglaNegocioException("No se puede eliminar el control: el lote ya esta en el almacen");
		}
		super.eliminar(id);
	}
}
