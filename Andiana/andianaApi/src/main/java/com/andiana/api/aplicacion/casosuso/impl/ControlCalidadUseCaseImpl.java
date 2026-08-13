package com.andiana.api.aplicacion.casosuso.impl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import com.andiana.api.aplicacion.casosuso.entrada.IControlCalidadUseCase;
import com.andiana.api.aplicacion.util.Validaciones;
import com.andiana.api.dominio.entidades.ControlCalidad;
import com.andiana.api.dominio.repositorio.IControlCalidadRepositorio;
import com.andiana.api.dominio.repositorio.IInventarioProductoRepositorio;
import com.andiana.api.dominio.repositorio.ILoteProduccionRepositorio;

public class ControlCalidadUseCaseImpl implements IControlCalidadUseCase {

	public static final String APROBADO = "APROBADO";
	public static final String OBSERVADO = "OBSERVADO";
	public static final String RECHAZADO = "RECHAZADO";

	private final IControlCalidadRepositorio repositorio;
	private final ILoteProduccionRepositorio loteRepositorio;
	private final IInventarioProductoRepositorio inventarioRepositorio;

	public ControlCalidadUseCaseImpl(IControlCalidadRepositorio repositorio, ILoteProduccionRepositorio loteRepositorio,
			IInventarioProductoRepositorio inventarioRepositorio) {
		this.repositorio = repositorio;
		this.loteRepositorio = loteRepositorio;
		this.inventarioRepositorio = inventarioRepositorio;
	}

	@Override
	public ControlCalidad guardar(ControlCalidad nuevoControlCalidad) {
		nuevoControlCalidad.setResultado(Validaciones.normalizar(nuevoControlCalidad.getResultado()));
		nuevoControlCalidad.setObservaciones(Validaciones.normalizar(nuevoControlCalidad.getObservaciones()));

		Validaciones.obligatorio(nuevoControlCalidad.getIdLote(), "lote");
		Validaciones.unoDe(nuevoControlCalidad.getResultado(), "resultado", APROBADO, OBSERVADO, RECHAZADO);

		if (nuevoControlCalidad.getFechaControl() == null) {
			nuevoControlCalidad.setFechaControl(LocalDateTime.now());
		}

		Validaciones.enRango(nuevoControlCalidad.getPh(), "pH", 0, 14);
		Validaciones.enRango(nuevoControlCalidad.getBrix(), "grados Brix", 0, 30);
		Validaciones.enRango(nuevoControlCalidad.getTemperatura(), "temperatura", -10, 60);

		if (loteRepositorio.buscarPorId(nuevoControlCalidad.getIdLote()).isEmpty()) {
			throw new RuntimeException("El lote indicado no existe");
		}

		if (!APROBADO.equals(nuevoControlCalidad.getResultado())
				&& !inventarioRepositorio.buscarPorLote(nuevoControlCalidad.getIdLote()).isEmpty()) {
			throw new RuntimeException("Ese lote ya está en el inventario: para cambiar el resultado hay"
					+ " que sacarlo del inventario primero");
		}

		return repositorio.guardar(nuevoControlCalidad);
	}

	@Override
	public ControlCalidad buscarPorId(int idControl) {
		return repositorio.buscarPorId(idControl)
				.orElseThrow(() -> new RuntimeException("Control de calidad no encontrado"));
	}

	/**
	 * Lo ultimo registrado va arriba. El listado se pagina, asi que en orden
	 * ascendente lo que se acaba de crear cae en la ultima pagina: el usuario
	 * vuelve del formulario, no lo ve, y cree que no se guardo.
	 */
	@Override
	public List<ControlCalidad> listarTodos() {
		return repositorio.listarTodos().stream()
				.sorted(Comparator.comparing(ControlCalidad::getIdControl,
						Comparator.nullsLast(Comparator.reverseOrder())))
				.toList();
	}

	@Override
	public void eliminar(int idControl) {
		ControlCalidad control = buscarPorId(idControl);
		if (!inventarioRepositorio.buscarPorLote(control.getIdLote()).isEmpty()) {
			throw new RuntimeException("No se puede eliminar el control: el lote ya está en el inventario");
		}
		repositorio.eliminar(idControl);
	}
}
