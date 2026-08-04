package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.IDetalleSolicitudUseCase;
import com.bodega.control.dominio.entidades.DetalleSolicitud;
import com.bodega.control.dominio.repositorio.IDetalleSolicitudRepositorio;

public class DetalleSolicitudUseCaseImpl implements IDetalleSolicitudUseCase {

    private final IDetalleSolicitudRepositorio repositorio;

    public DetalleSolicitudUseCaseImpl(IDetalleSolicitudRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public DetalleSolicitud guardar(DetalleSolicitud nuevoDetalleSolicitud) {
        return repositorio.guardar(nuevoDetalleSolicitud);
    }

    @Override
    public DetalleSolicitud buscarPorId(int idDetalleSolicitud) {
        return repositorio.buscarPorid(idDetalleSolicitud)
                .orElseThrow(() -> new RuntimeException("Detalle de solicitud no encontrado"));
    }

    @Override
    public List<DetalleSolicitud> listarTodos() {
        return repositorio.listarTodos();
    }

    @Override
    public void eliminar(int idDetalleSolicitud) {
        repositorio.eliminar(idDetalleSolicitud);
    }

	@Override
	public DetalleSolicitud buscarPorid(int DetalleSolicitud) {
		// TODO Auto-generated method stub
		return null;
	}

}