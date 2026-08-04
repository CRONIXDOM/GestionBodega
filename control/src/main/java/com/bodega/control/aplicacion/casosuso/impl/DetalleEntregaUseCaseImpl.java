package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.IDetalleEntregaUseCase;
import com.bodega.control.dominio.entidades.DetalleEntrega;
import com.bodega.control.dominio.repositorio.IDetalleEntregaRepositorio;

public class DetalleEntregaUseCaseImpl implements IDetalleEntregaUseCase {

    private final IDetalleEntregaRepositorio repositorio;

    public DetalleEntregaUseCaseImpl(IDetalleEntregaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public DetalleEntrega guardar(DetalleEntrega nuevoDetalleEntrega) {
        return repositorio.guardar(nuevoDetalleEntrega);
    }

    @Override
    public DetalleEntrega buscarPorid(int idDetalleEntrega) {
        return repositorio.buscarPorid(idDetalleEntrega)
                .orElseThrow(() -> new RuntimeException("Detalle de entrega no encontrado"));
    }

    @Override
    public List<DetalleEntrega> listarTodos() {
        return repositorio.listarTodos();
    }

    @Override
    public void eliminar(int idDetalleEntrega) {
        repositorio.eliminar(idDetalleEntrega);
    }

}