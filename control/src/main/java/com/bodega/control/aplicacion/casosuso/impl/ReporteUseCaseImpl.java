package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.IReporteUseCase;
import com.bodega.control.dominio.entidades.Reporte;
import com.bodega.control.dominio.repositorio.IReporteRepositorio;

public class ReporteUseCaseImpl implements IReporteUseCase {

    private final IReporteRepositorio repositorio;

    public ReporteUseCaseImpl(IReporteRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Reporte guardar(Reporte nuevoReporte) {
        return repositorio.guardar(nuevoReporte);
    }

    @Override
    public Reporte buscarPorId(int idReporte) {
        return repositorio.buscarPorid(idReporte)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
    }

    @Override
    public List<Reporte> listarTodos() {
        return repositorio.listarTodos();
    }

    @Override
    public void eliminar(int idReporte) {
        repositorio.eliminar(idReporte);
    }

	@Override
	public Reporte buscarPorid(int Reporte) {
		return repositorio.buscarPorid(Reporte)
				.orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
	}

}