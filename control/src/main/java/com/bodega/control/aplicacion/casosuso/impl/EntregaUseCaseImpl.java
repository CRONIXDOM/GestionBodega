package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.IEntregaUseCase;
import com.bodega.control.aplicacion.util.Validaciones;
import com.bodega.control.dominio.entidades.Entrega;
import com.bodega.control.dominio.repositorio.IEntregaRepositorio;

public class EntregaUseCaseImpl implements IEntregaUseCase {

	private final IEntregaRepositorio repositorio;

	public EntregaUseCaseImpl(IEntregaRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public Entrega guardar(Entrega nuevaEntrega) {
		nuevaEntrega.setResponsableEntrega(Validaciones.normalizar(nuevaEntrega.getResponsableEntrega()));
		Validaciones.obligatorio(nuevaEntrega.getResponsableEntrega(), "responsable de la entrega");
		Validaciones.obligatorioValor(nuevaEntrega.getFechaEntrega(), "fecha de la entrega");

		return repositorio.guardar(nuevaEntrega);
	}

	@Override
	public List<Entrega> listarTodos() {
		return repositorio.listarTodos();
	}

	@Override
	public void eliminar(int idEntrega) {
		repositorio.eliminar(idEntrega);
	}

	@Override
	public Entrega buscarPorid(int Entrega) {
		return repositorio.buscarPorid(Entrega)
				.orElseThrow(() -> new RuntimeException("Entrega no encontrada"));
	}

	@Override
	public Entrega buscarPorId(int idEntrega) {
		return repositorio.buscarPorid(idEntrega)
				.orElseThrow(() -> new RuntimeException("Entrega no encontrada"));
	}

}