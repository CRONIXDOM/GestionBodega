package com.translog.api.aplicacion.casosuso.impl;

import java.util.Comparator;
import java.util.List;

import com.translog.api.aplicacion.casosuso.entrada.ICiudadUseCase;
import com.translog.api.aplicacion.util.Validaciones;
import com.translog.api.dominio.entidades.Ciudad;
import com.translog.api.dominio.repositorio.ICiudadRepositorio;
import com.translog.api.dominio.repositorio.IEnvioRepositorio;
import com.translog.api.dominio.repositorio.IRutaRepositorio;

public class CiudadUseCaseImpl implements ICiudadUseCase {

	private final ICiudadRepositorio repositorio;
	private final IRutaRepositorio rutaRepositorio;
	private final IEnvioRepositorio envioRepositorio;

	public CiudadUseCaseImpl(ICiudadRepositorio repositorio, IRutaRepositorio rutaRepositorio,
			IEnvioRepositorio envioRepositorio) {
		this.repositorio = repositorio;
		this.rutaRepositorio = rutaRepositorio;
		this.envioRepositorio = envioRepositorio;
	}

	@Override
	public Ciudad guardar(Ciudad nuevaCiudad) {
		nuevaCiudad.setNombre(Validaciones.normalizar(nuevaCiudad.getNombre()));
		Validaciones.obligatorio(nuevaCiudad.getNombre(), "nombre de la ciudad");

		boolean repetida = repositorio.listarTodos().stream()
				.filter(otra -> !otra.getIdCiudad().equals(nuevaCiudad.getIdCiudad()))
				.anyMatch(otra -> otra.getNombre().equalsIgnoreCase(nuevaCiudad.getNombre()));
		if (repetida) {
			throw new RuntimeException("Ya existe la ciudad " + nuevaCiudad.getNombre());
		}

		return repositorio.guardar(nuevaCiudad);
	}

	@Override
	public Ciudad buscarPorId(int idCiudad) {
		return repositorio.buscarPorId(idCiudad)
				.orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
	}

	/** Lo ultimo registrado va arriba, que es donde el usuario lo busca. */
	@Override
	public List<Ciudad> listarTodos() {
		return repositorio.listarTodos().stream()
				.sorted(Comparator.comparing(Ciudad::getIdCiudad,
						Comparator.nullsLast(Comparator.reverseOrder())))
				.toList();
	}

	/** No se borra una ciudad que alguna ruta o algun envio sigan nombrando. */
	@Override
	public void eliminar(int idCiudad) {
		Ciudad ciudad = buscarPorId(idCiudad);

		boolean enRutas = rutaRepositorio.listarTodos().stream()
				.anyMatch(r -> idCiudad == r.getIdCiudadOrigen() || idCiudad == r.getIdCiudadDestino());
		boolean enEnvios = envioRepositorio.listarTodos().stream()
				.anyMatch(e -> idCiudad == e.getIdCiudadOrigen() || idCiudad == e.getIdCiudadDestino());
		if (enRutas || enEnvios) {
			throw new RuntimeException("No se puede eliminar " + ciudad.getNombre()
					+ ": la usa " + (enRutas ? "alguna ruta" : "algun envio"));
		}

		repositorio.eliminar(idCiudad);
	}

}
