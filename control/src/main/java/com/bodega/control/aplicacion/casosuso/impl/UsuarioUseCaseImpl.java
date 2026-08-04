package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.IUsuarioUseCase;
import com.bodega.control.dominio.entidades.Usuario;
import com.bodega.control.dominio.repositorio.IUsuarioRepositorio;

public class UsuarioUseCaseImpl implements IUsuarioUseCase {

	private final IUsuarioRepositorio repositorio;

	public UsuarioUseCaseImpl(IUsuarioRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public Usuario guardar(Usuario nuevoUsuario) {
		return repositorio.guardar(nuevoUsuario);
	}

	@Override
	public Usuario buscarPorId(int idUsuario) {
		return repositorio.buscarPorid(idUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	}

	@Override
	public List<Usuario> listarTodos() {
		return repositorio.listarTodos();
	}

	@Override
	public void eliminar(int idUsuario) {
		repositorio.eliminar(idUsuario);
	}

	@Override
	public Usuario buscarPorid(int Usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> listarTodo() {
		// TODO Auto-generated method stub
		return null;
	}

}