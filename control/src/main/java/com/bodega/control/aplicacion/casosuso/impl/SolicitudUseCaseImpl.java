package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.ISolicitudUseCase;
import com.bodega.control.dominio.entidades.Solicitud;
import com.bodega.control.dominio.repositorio.ISolicitudRepositorio;

public class SolicitudUseCaseImpl implements ISolicitudUseCase {

    private final ISolicitudRepositorio repositorio;

    public SolicitudUseCaseImpl(ISolicitudRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Solicitud guardar(Solicitud nuevaSolicitud) {
        return repositorio.guardar(nuevaSolicitud);
    }

    @Override
    public Solicitud buscarPorId(int idSolicitud) {
        return repositorio.buscarPorid(idSolicitud)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
    }

    @Override
    public List<Solicitud> listarTodos() {
        return repositorio.listarTodos();
    }

    @Override
    public void eliminar(int idSolicitud) {
        repositorio.eliminar(idSolicitud);
    }

	@Override
	public Solicitud buscarPorid(int Solicitud) {
		// TODO Auto-generated method stub
		return null;
	}

}