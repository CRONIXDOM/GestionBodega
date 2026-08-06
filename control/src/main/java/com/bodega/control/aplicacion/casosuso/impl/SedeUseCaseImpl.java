package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.ISedeUseCase;
import com.bodega.control.aplicacion.util.Validaciones;
import com.bodega.control.dominio.entidades.Sede;
import com.bodega.control.dominio.repositorio.ISedeRepositorio;

public class SedeUseCaseImpl implements ISedeUseCase {

	private final ISedeRepositorio repositorio;

	public SedeUseCaseImpl(ISedeRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public Sede guardar(Sede nuevaSede) {
		nuevaSede.setNombreSede(Validaciones.normalizar(nuevaSede.getNombreSede()));
		nuevaSede.setDireccion(Validaciones.normalizar(nuevaSede.getDireccion()));
		nuevaSede.setDescripcion(Validaciones.normalizar(nuevaSede.getDescripcion()));

		Validaciones.obligatorio(nuevaSede.getNombreSede(), "nombre de la sede");
		Validaciones.obligatorio(nuevaSede.getDireccion(), "dirección");
		Validaciones.obligatorio(nuevaSede.getDescripcion(), "descripción");
		Validaciones.obligatorioPositivo(nuevaSede.getCapacidad(), "capacidad");

		List<Sede> existentes = repositorio.listarTodos();
		Validaciones.noRepetido(existentes, Sede::getIdSede, Sede::getNombreSede,
				nuevaSede.getIdSede(), nuevaSede.getNombreSede(), "una sede con el nombre");
		Validaciones.noRepetido(existentes, Sede::getIdSede, Sede::getDireccion,
				nuevaSede.getIdSede(), nuevaSede.getDireccion(), "una sede en la dirección");

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
