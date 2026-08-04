package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.IProductoUseCase;
import com.bodega.control.dominio.entidades.Producto;
import com.bodega.control.dominio.repositorio.IProductoRepositorio;

public class ProductoUseCaseImpl implements IProductoUseCase {

    private final IProductoRepositorio repositorio;

    public ProductoUseCaseImpl(IProductoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Producto guardar(Producto nuevoProducto) {
        return repositorio.guardar(nuevoProducto);
    }

    @Override
    public Producto buscarPorId(int idProducto) {
        return repositorio.buscarPorid(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Override
    public List<Producto> listarTodos() {
        return repositorio.listarTodos();
    }

    @Override
    public void eliminar(int idProducto) {
        repositorio.eliminar(idProducto);
    }

	@Override
	public Producto buscarPorid(int Producto) {
		// TODO Auto-generated method stub
		return null;
	}

}