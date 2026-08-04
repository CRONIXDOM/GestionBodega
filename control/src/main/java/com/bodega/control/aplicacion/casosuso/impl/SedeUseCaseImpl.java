package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.ISedeUseCase;
import com.bodega.control.dominio.entidades.Sede;
import com.bodega.control.dominio.repositorio.ISedeRepositorio;

public class SedeUseCaseImpl implements ISedeUseCase {

	private final ISedeRepositorio repositorio;

	public SedeUseCaseImpl(ISedeRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public Sede guardar(Sede nuevaSede) {
		return repositorio.guardar(nuevaSede);
	}

	@Override
	public Sede buscarPorId(int idSede) {
		return repositorio.buscarPorid(idSede).orElseThrow(() -> new RuntimeException("Sede no encontrada"));
	}

	@Override
	public List<Sede> listarTodos() {
		return repositorio.listarTodos();
	}

	@Override
	public void eliminar(int idSede) {
		repositorio.eliminar(idSede);
	}

}
