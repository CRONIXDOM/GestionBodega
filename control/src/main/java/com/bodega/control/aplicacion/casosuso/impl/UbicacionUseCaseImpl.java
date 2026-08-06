package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.IUbicacionUseCase;
import com.bodega.control.aplicacion.util.Validaciones;
import com.bodega.control.dominio.entidades.Ubicacion;
import com.bodega.control.dominio.repositorio.IUbicacionRepositorio;

public class UbicacionUseCaseImpl implements IUbicacionUseCase {

	private final IUbicacionRepositorio repositorio;

	public UbicacionUseCaseImpl(IUbicacionRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public Ubicacion guardar(Ubicacion nuevaUbicacion) {
		if (nuevaUbicacion.getZona() != null && nuevaUbicacion.getZona().getIdZona() == null) {
			nuevaUbicacion.setZona(null);
		}
		if (nuevaUbicacion.getSede() != null && nuevaUbicacion.getSede().getIdSede() == null) {
			nuevaUbicacion.setSede(null);
		}

		nuevaUbicacion.setCodigoUbicacion(Validaciones.normalizar(nuevaUbicacion.getCodigoUbicacion()));
		Validaciones.obligatorio(nuevaUbicacion.getCodigoUbicacion(), "código de la ubicación");
		if (nuevaUbicacion.getSede() == null) {
			throw new RuntimeException("El campo sede es obligatorio");
		}
		Validaciones.noRepetido(repositorio.listarTodos(), Ubicacion::getIdUbicacion, Ubicacion::getCodigoUbicacion,
				nuevaUbicacion.getIdUbicacion(), nuevaUbicacion.getCodigoUbicacion(),
				"una ubicación con el código");

		return repositorio.guardar(nuevaUbicacion);
	}

	@Override
	public Ubicacion buscarPorId(int idUbicacion) {
		return repositorio.buscarPorid(idUbicacion).orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));
	}

	@Override
	public List<Ubicacion> listarTodos() {
		return repositorio.listarTodos();
	}

	@Override
	public void eliminar(int idUbicacion) {
		repositorio.eliminar(idUbicacion);
	}

	@Override
	public Ubicacion buscarPorid(int idUbicacion) {
		return repositorio.buscarPorid(idUbicacion).orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));
	}

}