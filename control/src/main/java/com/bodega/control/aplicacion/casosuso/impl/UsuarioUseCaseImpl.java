package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;
import java.util.regex.Pattern;

import com.bodega.control.aplicacion.casosuso.entrada.IUsuarioUseCase;
import com.bodega.control.dominio.entidades.Usuario;
import com.bodega.control.dominio.repositorio.IUsuarioRepositorio;

public class UsuarioUseCaseImpl implements IUsuarioUseCase {

	private static final Pattern SOLO_LETRAS = Pattern.compile("^[\\p{L} '\\-]+$");

	private final IUsuarioRepositorio repositorio;

	public UsuarioUseCaseImpl(IUsuarioRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public Usuario guardar(Usuario nuevoUsuario) {
		if (nuevoUsuario.getNombreUsuario() == null || nuevoUsuario.getNombreUsuario().isBlank()) {
			throw new RuntimeException("El nombre del usuario es obligatorio");
		}
		// el nombre del usuario se conserva tal cual se escribe, porque es el que
		// se usa para iniciar sesion junto a la contrasena.
		nuevoUsuario.setNombreUsuario(nuevoUsuario.getNombreUsuario().trim());
		if (nuevoUsuario.getApellidoUsuario() != null) {
			nuevoUsuario.setApellidoUsuario(nuevoUsuario.getApellidoUsuario().trim());
		}

		validarSinNumeros(nuevoUsuario.getNombreUsuario(), "nombre");
		if (nuevoUsuario.getApellidoUsuario() != null && !nuevoUsuario.getApellidoUsuario().isBlank()) {
			validarSinNumeros(nuevoUsuario.getApellidoUsuario(), "apellido");
		}
		validarNombreNoRepetido(nuevoUsuario);

		return repositorio.guardar(nuevoUsuario);
	}

	private static void validarSinNumeros(String valor, String campo) {
		if (!SOLO_LETRAS.matcher(valor).matches()) {
			throw new RuntimeException(
					"El " + campo + " solo puede contener letras: \"" + valor + "\" no es valido");
		}
	}

	private void validarNombreNoRepetido(Usuario usuario) {
		boolean repetido = repositorio.listarTodos().stream()
				.filter(otro -> usuario.getIdUsuario() == null || !usuario.getIdUsuario().equals(otro.getIdUsuario()))
				.anyMatch(otro -> usuario.getNombreUsuario().equalsIgnoreCase(otro.getNombreUsuario()));
		if (repetido) {
			throw new RuntimeException("Ya existe un usuario con el nombre \"" + usuario.getNombreUsuario() + "\"");
		}
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
		return repositorio.buscarPorid(Usuario)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	}

	@Override
	public List<Usuario> listarTodo() {
		return listarTodos();
	}

}