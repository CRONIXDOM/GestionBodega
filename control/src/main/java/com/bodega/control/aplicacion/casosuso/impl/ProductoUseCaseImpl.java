package com.bodega.control.aplicacion.casosuso.impl;

import java.util.List;

import com.bodega.control.aplicacion.casosuso.entrada.IProductoUseCase;
import com.bodega.control.aplicacion.util.Validaciones;
import com.bodega.control.dominio.entidades.Producto;
import com.bodega.control.dominio.repositorio.IProductoRepositorio;

public class ProductoUseCaseImpl implements IProductoUseCase {

    private final IProductoRepositorio repositorio;

    public ProductoUseCaseImpl(IProductoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Producto guardar(Producto nuevoProducto) {
        nuevoProducto.setNombreProducto(Validaciones.normalizar(nuevoProducto.getNombreProducto()));
        nuevoProducto.setCodigoProducto(Validaciones.normalizar(nuevoProducto.getCodigoProducto()));
        nuevoProducto.setCantidadProducto(Validaciones.normalizar(nuevoProducto.getCantidadProducto()));

        Validaciones.obligatorio(nuevoProducto.getNombreProducto(), "nombre del producto");
        Validaciones.obligatorio(nuevoProducto.getCodigoProducto(), "código del producto");
        Validaciones.obligatorioPositivo(nuevoProducto.getUnidadesPorCaja(), "unidades por caja");

        List<Producto> existentes = repositorio.listarTodos();
        Validaciones.noRepetido(existentes, Producto::getIdProducto, Producto::getCodigoProducto,
                nuevoProducto.getIdProducto(), nuevoProducto.getCodigoProducto(), "un producto con el código");
        Validaciones.noRepetido(existentes, Producto::getIdProducto, Producto::getNombreProducto,
                nuevoProducto.getIdProducto(), nuevoProducto.getNombreProducto(), "un producto con el nombre");

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
		return null;
	}

}