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
	public Ubicacion buscarPorid(int Ubicacion) {
		// TODO Auto-generated method stub
		return null;
	}

}