package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.ICredencialesUseCase;
import com.bodega.control.dominio.entidades.Credenciales;
import com.bodega.control.dominio.repositorio.ICredencialesRepositorio;


public class CredencialesUseCaseImpl implements ICredencialesUseCase {

	private final ICredencialesRepositorio repositorio;

	public CredencialesUseCaseImpl(ICredencialesRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public Credenciales guardar(Credenciales nuevaCredenciales) {

		return repositorio.guardar(nuevaCredenciales);
	}

	@Override
	public Credenciales buscarPorId(int idCredenciales) {

		return repositorio.buscarPorId(idCredenciales).orElseThrow(() -> new RuntimeException("Credencial no encontrada"));
	}

	@Override
	public List<Credenciales> listarTodos() {

		return repositorio.listarTodos();
	}

	@Override
	public void eliminar(int idCredenciales) {
		repositorio.eliminar(idCredenciales);

	}

	@Override
	public List<Credenciales> listarCredenciales() {

		return repositorio.listarCredenciales();
	}

	@Override
	public List<Credenciales> buscarCredencialesNombre(String nombre) {

		return repositorio.buscarCredencialesNombre(nombre);
	}

	@Override
	public List<Credenciales> buscarCredencialesEstado(String nombre, boolean estado) {
		
		return repositorio.buscarCredencialesEstado(nombre, estado);

  }
}

