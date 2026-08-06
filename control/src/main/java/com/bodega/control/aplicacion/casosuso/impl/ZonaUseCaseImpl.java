package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.IZonaUseCase;
import com.bodega.control.aplicacion.util.Validaciones;
import com.bodega.control.dominio.entidades.Zona;
import com.bodega.control.dominio.repositorio.IZonaRepositorio;

public class ZonaUseCaseImpl implements IZonaUseCase {

    private final IZonaRepositorio repositorio;

    public ZonaUseCaseImpl(IZonaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Zona guardar(Zona nuevaZona) {
		nuevaZona.setNombreZona(Validaciones.normalizar(nuevaZona.getNombreZona()));
		nuevaZona.setDescripcion(Validaciones.normalizar(nuevaZona.getDescripcion()));
		Validaciones.obligatorio(nuevaZona.getNombreZona(), "nombre de la zona");
		Validaciones.noRepetido(repositorio.listarTodos(), Zona::getIdZona, Zona::getNombreZona,
				nuevaZona.getIdZona(), nuevaZona.getNombreZona(), "una zona con el nombre");

        return repositorio.guardar(nuevaZona);
    }

    @Override
    public Zona buscarPorId(int idZona) {
        return repositorio.buscarPorid(idZona)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));
    }

    @Override
    public List<Zona> listarTodos() {
        return repositorio.listarTodos();
    }

    @Override
    public void eliminar(int idZona) {
        repositorio.eliminar(idZona);
    }

	@Override
	public Zona buscarPorid(int Zona) {
		return repositorio.buscarPorid(Zona)
				.orElseThrow(() -> new RuntimeException("Zona no encontrada"));
	}

	@Override
	public List<Zona> listarTodo() {
		return listarTodos();
	}

}