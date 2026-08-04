package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.IRegistroUseCase;
import com.bodega.control.dominio.entidades.Registro;
import com.bodega.control.dominio.repositorio.IRegistroRepositorio;

public class RegistroUseCaseImpl implements IRegistroUseCase {

	private final IRegistroRepositorio repositorio;

	public RegistroUseCaseImpl(IRegistroRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public Registro guardar(Registro nuevoRegistro) {
		return repositorio.guardar(nuevoRegistro);
	}

	@Override
	public Registro buscarPorId(int idRegistro) {
		return repositorio.buscarPorid(idRegistro).orElseThrow(() -> new RuntimeException("Registro no encontrado"));
	}

	@Override
	public List<Registro> listarTodos() {
		return repositorio.listarTodos();
	}

	@Override
	public void eliminar(int idRegistro) {
		repositorio.eliminar(idRegistro);
	}

	@Override
	public Registro buscarPorid(int Registro) {
		// TODO Auto-generated method stub
		return null;
	}

}