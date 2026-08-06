package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;
import java.util.function.Function;

import com.bodega.control.aplicacion.casosuso.entrada.IRegistroUseCase;
import com.bodega.control.dominio.entidades.DetalleEntrega;
import com.bodega.control.dominio.entidades.Lote;
import com.bodega.control.dominio.entidades.Registro;
import com.bodega.control.dominio.entidades.Tipo;
import com.bodega.control.dominio.entidades.Ubicacion;
import com.bodega.control.dominio.entidades.UsuarioRol;
import com.bodega.control.dominio.repositorio.IRegistroRepositorio;

public class RegistroUseCaseImpl implements IRegistroUseCase {

	private final IRegistroRepositorio repositorio;

	public RegistroUseCaseImpl(IRegistroRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public Registro guardar(Registro nuevoRegistro) {
		if (normalizarId(nuevoRegistro.getLote(), Lote::getIdLote) == null) {
			nuevoRegistro.setLote(null);
		}
		if (normalizarId(nuevoRegistro.getTipo(), Tipo::getIdTipo) == null) {
			nuevoRegistro.setTipo(null);
		}
		if (normalizarId(nuevoRegistro.getUbicacion(), Ubicacion::getIdUbicacion) == null) {
			nuevoRegistro.setUbicacion(null);
		}
		if (normalizarId(nuevoRegistro.getDetalleEntrega(), DetalleEntrega::getIdDetalleEntrega) == null) {
			nuevoRegistro.setDetalleEntrega(null);
		}
		if (normalizarId(nuevoRegistro.getUsuarioRol(), UsuarioRol::getIdUsuarioRol) == null) {
			nuevoRegistro.setUsuarioRol(null);
		}
		return repositorio.guardar(nuevoRegistro);
	}

	private static <T> Integer normalizarId(T objeto, Function<T, Integer> getId) {
		return objeto == null ? null : getId.apply(objeto);
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
	public Registro buscarPorid(int idRegistro) {
		return repositorio.buscarPorid(idRegistro).orElseThrow(() -> new RuntimeException("Registro no encontrado"));
	}

}