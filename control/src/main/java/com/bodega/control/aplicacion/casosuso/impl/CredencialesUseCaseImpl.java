package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.ICredencialesUseCase;
import com.bodega.control.aplicacion.util.Validaciones;
import com.bodega.control.dominio.entidades.Credenciales;
import com.bodega.control.dominio.repositorio.ICredencialesRepositorio;


public class CredencialesUseCaseImpl implements ICredencialesUseCase {

	private final ICredencialesRepositorio repositorio;

	public CredencialesUseCaseImpl(ICredencialesRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public Credenciales guardar(Credenciales nuevaCredenciales) {
		// las credenciales se guardan EXACTAMENTE como las escribe el usuario:
		// tocar el usuario o la contrasena romperia el inicio de sesion.
		if (nuevaCredenciales.getUsuario() != null) {
			nuevaCredenciales.setUsuario(nuevaCredenciales.getUsuario().trim().replaceAll("\\s+", " "));
		}
		if (nuevaCredenciales.getCorreo() != null) {
			nuevaCredenciales.setCorreo(nuevaCredenciales.getCorreo().trim());
		}

		Validaciones.obligatorio(nuevaCredenciales.getUsuario(), "usuario");
		Validaciones.obligatorio(nuevaCredenciales.getContrasena(), "contraseña");
		Validaciones.obligatorio(nuevaCredenciales.getCorreo(), "correo");
		if (!nuevaCredenciales.getCorreo().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
			throw new RuntimeException("El correo no tiene un formato válido");
		}

		// dos credenciales con el mismo usuario harian que el login no supiera
		// cual de las dos contrasenas es la buena.
		List<Credenciales> existentes = repositorio.listarTodos();
		Validaciones.noRepetido(existentes, Credenciales::getIdCredenciales, Credenciales::getUsuario,
				nuevaCredenciales.getIdCredenciales(), nuevaCredenciales.getUsuario(),
				"una credencial para el usuario");
		Validaciones.noRepetido(existentes, Credenciales::getIdCredenciales, Credenciales::getCorreo,
				nuevaCredenciales.getIdCredenciales(), nuevaCredenciales.getCorreo(),
				"una credencial con el correo");

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

