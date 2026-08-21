package com.translog.api.aplicacion.casosuso.impl;

import java.util.Comparator;
import java.util.List;

import com.translog.api.aplicacion.casosuso.entrada.IVehiculoUseCase;
import com.translog.api.aplicacion.util.Validaciones;
import com.translog.api.dominio.entidades.Vehiculo;
import com.translog.api.dominio.repositorio.IDespachoRepositorio;
import com.translog.api.dominio.repositorio.IVehiculoRepositorio;

public class VehiculoUseCaseImpl implements IVehiculoUseCase {

	public static final String DISPONIBLE = "DISPONIBLE";
	public static final String EN_RUTA = "EN_RUTA";
	public static final String MANTENIMIENTO = "MANTENIMIENTO";

	private final IVehiculoRepositorio repositorio;
	private final IDespachoRepositorio despachoRepositorio;

	public VehiculoUseCaseImpl(IVehiculoRepositorio repositorio, IDespachoRepositorio despachoRepositorio) {
		this.repositorio = repositorio;
		this.despachoRepositorio = despachoRepositorio;
	}

	@Override
	public Vehiculo guardar(Vehiculo nuevoVehiculo) {
		nuevoVehiculo.setPlaca(Validaciones.normalizar(nuevoVehiculo.getPlaca()));
		nuevoVehiculo.setEstado(Validaciones.normalizar(nuevoVehiculo.getEstado()));

		Validaciones.obligatorio(nuevoVehiculo.getPlaca(), "placa");
		Validaciones.mayorQueCero(nuevoVehiculo.getCapacidadMaxima(), "capacidad máxima");
		Validaciones.unoDe(nuevoVehiculo.getEstado(), "estado", DISPONIBLE, EN_RUTA, MANTENIMIENTO);

		boolean repetida = repositorio.listarTodos().stream()
				.filter(otro -> !otro.getIdVehiculo().equals(nuevoVehiculo.getIdVehiculo()))
				.anyMatch(otro -> otro.getPlaca().equalsIgnoreCase(nuevoVehiculo.getPlaca()));
		if (repetida) {
			throw new RuntimeException("Ya hay un vehículo con la placa " + nuevoVehiculo.getPlaca());
		}

		return repositorio.guardar(nuevoVehiculo);
	}

	@Override
	public Vehiculo buscarPorId(int idVehiculo) {
		return repositorio.buscarPorId(idVehiculo)
				.orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
	}

	@Override
	public List<Vehiculo> listarTodos() {
		return repositorio.listarTodos().stream()
				.sorted(Comparator.comparing(Vehiculo::getIdVehiculo,
						Comparator.nullsLast(Comparator.reverseOrder())))
				.toList();
	}

	@Override
	public void eliminar(int idVehiculo) {
		Vehiculo vehiculo = buscarPorId(idVehiculo);
		boolean enUso = despachoRepositorio.listarTodos().stream()
				.anyMatch(d -> idVehiculo == d.getIdVehiculo());
		if (enUso) {
			throw new RuntimeException("No se puede eliminar el vehículo " + vehiculo.getPlaca()
					+ ": ya figura en un despacho");
		}
		repositorio.eliminar(idVehiculo);
	}

}
