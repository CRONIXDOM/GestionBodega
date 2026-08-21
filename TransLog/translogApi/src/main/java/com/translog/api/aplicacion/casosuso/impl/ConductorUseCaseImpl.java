package com.translog.api.aplicacion.casosuso.impl;

import java.util.Comparator;
import java.util.List;

import com.translog.api.aplicacion.casosuso.entrada.IConductorUseCase;
import com.translog.api.aplicacion.util.Validaciones;
import com.translog.api.dominio.entidades.Conductor;
import com.translog.api.dominio.repositorio.IConductorRepositorio;
import com.translog.api.dominio.repositorio.IDespachoRepositorio;

public class ConductorUseCaseImpl implements IConductorUseCase {

	public static final String HABILITADO = "HABILITADO";
	public static final String NO_HABILITADO = "NO_HABILITADO";

	private final IConductorRepositorio repositorio;
	private final IDespachoRepositorio despachoRepositorio;

	public ConductorUseCaseImpl(IConductorRepositorio repositorio, IDespachoRepositorio despachoRepositorio) {
		this.repositorio = repositorio;
		this.despachoRepositorio = despachoRepositorio;
	}

	@Override
	public Conductor guardar(Conductor nuevoConductor) {
		nuevoConductor.setNombre(Validaciones.normalizar(nuevoConductor.getNombre()));
		nuevoConductor.setLicencia(Validaciones.normalizar(nuevoConductor.getLicencia()));
		nuevoConductor.setEstado(Validaciones.normalizar(nuevoConductor.getEstado()));

		Validaciones.obligatorio(nuevoConductor.getNombre(), "nombre");
		Validaciones.obligatorio(nuevoConductor.getLicencia(), "licencia");
		Validaciones.unoDe(nuevoConductor.getEstado(), "estado", HABILITADO, NO_HABILITADO);

		boolean repetida = repositorio.listarTodos().stream()
				.filter(otro -> !otro.getIdConductor().equals(nuevoConductor.getIdConductor()))
				.anyMatch(otro -> otro.getLicencia().equalsIgnoreCase(nuevoConductor.getLicencia()));
		if (repetida) {
			throw new RuntimeException("Ya hay un conductor con la licencia " + nuevoConductor.getLicencia());
		}

		return repositorio.guardar(nuevoConductor);
	}

	@Override
	public Conductor buscarPorId(int idConductor) {
		return repositorio.buscarPorId(idConductor)
				.orElseThrow(() -> new RuntimeException("Conductor no encontrado"));
	}

	@Override
	public List<Conductor> listarTodos() {
		return repositorio.listarTodos().stream()
				.sorted(Comparator.comparing(Conductor::getIdConductor,
						Comparator.nullsLast(Comparator.reverseOrder())))
				.toList();
	}

	@Override
	public void eliminar(int idConductor) {
		Conductor conductor = buscarPorId(idConductor);
		boolean enUso = despachoRepositorio.listarTodos().stream()
				.anyMatch(d -> idConductor == d.getIdConductor());
		if (enUso) {
			throw new RuntimeException("No se puede eliminar a " + conductor.getNombre()
					+ ": ya figura en un despacho");
		}
		repositorio.eliminar(idConductor);
	}

}
