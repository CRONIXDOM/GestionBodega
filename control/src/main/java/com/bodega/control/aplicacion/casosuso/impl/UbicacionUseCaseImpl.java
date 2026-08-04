package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.IUbicacionUseCase;
import com.bodega.control.dominio.entidades.Ubicacion;
import com.bodega.control.dominio.repositorio.IUbicacionRepositorio;

public class UbicacionUseCaseImpl implements IUbicacionUseCase {

	private final IUbicacionRepositorio repositorio;

	public UbicacionUseCaseImpl(IUbicacionRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public Ubicacion guardar(Ubicacion nuevaUbicacion) {
		// el mapper crea un objeto Zona/Sede "cascaron" (id=null) cuando el formulario
		// no elige una opcion; hay que normalizarlo a null real o Hibernate intenta
		// guardarlo como una entidad nueva en vez de tratarlo como ausente.
		if (nuevaUbicacion.getZona() != null && nuevaUbicacion.getZona().getIdZona() == null) {
			nuevaUbicacion.setZona(null);
		}
		if (nuevaUbicacion.getSede() != null && nuevaUbicacion.getSede().getIdSede() == null) {
			nuevaUbicacion.setSede(null);
		}
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