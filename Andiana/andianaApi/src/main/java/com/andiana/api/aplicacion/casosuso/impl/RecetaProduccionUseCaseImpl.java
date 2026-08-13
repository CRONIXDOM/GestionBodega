package com.andiana.api.aplicacion.casosuso.impl;

import java.util.List;

import com.andiana.api.aplicacion.casosuso.entrada.IRecetaProduccionUseCase;
import com.andiana.api.aplicacion.util.Validaciones;
import com.andiana.api.dominio.entidades.RecetaProduccion;
import com.andiana.api.dominio.repositorio.IDetalleRecetaRepositorio;
import com.andiana.api.dominio.repositorio.IProductoRepositorio;
import com.andiana.api.dominio.repositorio.IRecetaProduccionRepositorio;

public class RecetaProduccionUseCaseImpl implements IRecetaProduccionUseCase {

	private final IRecetaProduccionRepositorio repositorio;
	private final IProductoRepositorio productoRepositorio;
	private final IDetalleRecetaRepositorio detalleRepositorio;

	public RecetaProduccionUseCaseImpl(IRecetaProduccionRepositorio repositorio,
			IProductoRepositorio productoRepositorio, IDetalleRecetaRepositorio detalleRepositorio) {
		this.repositorio = repositorio;
		this.productoRepositorio = productoRepositorio;
		this.detalleRepositorio = detalleRepositorio;
	}

	@Override
	public RecetaProduccion guardar(RecetaProduccion nuevoRecetaProduccion) {
		Validaciones.obligatorio(nuevoRecetaProduccion.getIdProducto(), "producto");
		Validaciones.mayorQueCero(nuevoRecetaProduccion.getVersion(), "versión");
		Validaciones.obligatorio(nuevoRecetaProduccion.getFechaVigencia(), "fecha de vigencia");

		if (nuevoRecetaProduccion.getEstado() == null) {
			nuevoRecetaProduccion.setEstado(Boolean.TRUE);
		}
		if (productoRepositorio.buscarPorId(nuevoRecetaProduccion.getIdProducto()).isEmpty()) {
			throw new RuntimeException("El producto indicado no existe");
		}

		boolean repetida = repositorio.buscarPorProducto(nuevoRecetaProduccion.getIdProducto()).stream()
				.filter(otra -> nuevoRecetaProduccion.getIdReceta() == null
						|| !nuevoRecetaProduccion.getIdReceta().equals(otra.getIdReceta()))
				.anyMatch(otra -> nuevoRecetaProduccion.getVersion().equals(otra.getVersion()));
		if (repetida) {
			throw new RuntimeException(
					"Ese producto ya tiene una receta con la versión " + nuevoRecetaProduccion.getVersion());
		}

		return repositorio.guardar(nuevoRecetaProduccion);
	}

	@Override
	public RecetaProduccion buscarPorId(int idReceta) {
		return repositorio.buscarPorId(idReceta).orElseThrow(() -> new RuntimeException("Receta no encontrada"));
	}

	@Override
	public List<RecetaProduccion> listarTodos() {
		return repositorio.listarTodos();
	}

	@Override
	public void eliminar(int idReceta) {
		int lineas = detalleRepositorio.buscarPorReceta(idReceta).size();
		if (lineas > 0) {
			throw new RuntimeException("No se puede eliminar la receta: todavía tiene " + lineas
					+ " materia(s) prima(s). Quítalas primero.");
		}
		buscarPorId(idReceta);
		repositorio.eliminar(idReceta);
	}
}
