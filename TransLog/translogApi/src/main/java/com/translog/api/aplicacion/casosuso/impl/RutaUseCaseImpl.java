package com.translog.api.aplicacion.casosuso.impl;

import java.util.Comparator;
import java.util.List;

import com.translog.api.aplicacion.casosuso.entrada.IRutaUseCase;
import com.translog.api.aplicacion.util.Validaciones;
import com.translog.api.dominio.entidades.Ruta;
import com.translog.api.dominio.repositorio.ICiudadRepositorio;
import com.translog.api.dominio.repositorio.IDespachoRepositorio;
import com.translog.api.dominio.repositorio.IRutaRepositorio;

public class RutaUseCaseImpl implements IRutaUseCase {

	private final IRutaRepositorio repositorio;
	private final ICiudadRepositorio ciudadRepositorio;
	private final IDespachoRepositorio despachoRepositorio;

	public RutaUseCaseImpl(IRutaRepositorio repositorio, ICiudadRepositorio ciudadRepositorio,
			IDespachoRepositorio despachoRepositorio) {
		this.repositorio = repositorio;
		this.ciudadRepositorio = ciudadRepositorio;
		this.despachoRepositorio = despachoRepositorio;
	}

	@Override
	public Ruta guardar(Ruta nuevaRuta) {
		Validaciones.obligatorio(nuevaRuta.getIdCiudadOrigen(), "ciudad de origen");
		Validaciones.obligatorio(nuevaRuta.getIdCiudadDestino(), "ciudad de destino");
		Validaciones.mayorQueCero(nuevaRuta.getDistanciaKm(), "distancia");

		if (nuevaRuta.getIdCiudadOrigen().equals(nuevaRuta.getIdCiudadDestino())) {
			throw new RuntimeException("El origen y el destino no pueden ser la misma ciudad");
		}
		existe(nuevaRuta.getIdCiudadOrigen(), "origen");
		existe(nuevaRuta.getIdCiudadDestino(), "destino");

		boolean repetida = repositorio.listarTodos().stream()
				.filter(otra -> !otra.getIdRuta().equals(nuevaRuta.getIdRuta()))
				.anyMatch(otra -> otra.getIdCiudadOrigen().equals(nuevaRuta.getIdCiudadOrigen())
						&& otra.getIdCiudadDestino().equals(nuevaRuta.getIdCiudadDestino()));
		if (repetida) {
			throw new RuntimeException("Ya existe una ruta entre esas dos ciudades");
		}

		return repositorio.guardar(nuevaRuta);
	}

	private void existe(Integer idCiudad, String cual) {
		if (ciudadRepositorio.buscarPorId(idCiudad).isEmpty()) {
			throw new RuntimeException("La ciudad de " + cual + " indicada no existe");
		}
	}

	@Override
	public Ruta buscarPorId(int idRuta) {
		return repositorio.buscarPorId(idRuta).orElseThrow(() -> new RuntimeException("Ruta no encontrada"));
	}

	@Override
	public List<Ruta> listarTodos() {
		return repositorio.listarTodos().stream()
				.sorted(Comparator.comparing(Ruta::getIdRuta,
						Comparator.nullsLast(Comparator.reverseOrder())))
				.toList();
	}

	/** Una ruta que ya tiene despachos programados no se borra. */
	@Override
	public void eliminar(int idRuta) {
		buscarPorId(idRuta);
		boolean enUso = despachoRepositorio.listarTodos().stream()
				.anyMatch(d -> idRuta == d.getIdRuta());
		if (enUso) {
			throw new RuntimeException("No se puede eliminar la ruta: ya tiene despachos programados");
		}
		repositorio.eliminar(idRuta);
	}

}
