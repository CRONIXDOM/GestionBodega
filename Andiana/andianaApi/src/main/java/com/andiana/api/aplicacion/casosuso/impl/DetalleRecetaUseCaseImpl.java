package com.andiana.api.aplicacion.casosuso.impl;

import java.util.List;

import com.andiana.api.aplicacion.casosuso.entrada.IDetalleRecetaUseCase;
import com.andiana.api.aplicacion.util.Validaciones;
import com.andiana.api.dominio.entidades.DetalleReceta;
import com.andiana.api.dominio.entidades.MateriaPrima;
import com.andiana.api.dominio.repositorio.IDetalleRecetaRepositorio;
import com.andiana.api.dominio.repositorio.IMateriaPrimaRepositorio;
import com.andiana.api.dominio.repositorio.IRecetaProduccionRepositorio;

public class DetalleRecetaUseCaseImpl implements IDetalleRecetaUseCase {

	private final IDetalleRecetaRepositorio repositorio;
	private final IRecetaProduccionRepositorio recetaRepositorio;
	private final IMateriaPrimaRepositorio materiaRepositorio;

	public DetalleRecetaUseCaseImpl(IDetalleRecetaRepositorio repositorio,
			IRecetaProduccionRepositorio recetaRepositorio, IMateriaPrimaRepositorio materiaRepositorio) {
		this.repositorio = repositorio;
		this.recetaRepositorio = recetaRepositorio;
		this.materiaRepositorio = materiaRepositorio;
	}

	@Override
	public DetalleReceta guardar(DetalleReceta nuevoDetalleReceta) {
		Validaciones.obligatorio(nuevoDetalleReceta.getIdReceta(), "receta");
		Validaciones.obligatorio(nuevoDetalleReceta.getIdMateria(), "materia prima");
		Validaciones.mayorQueCero(nuevoDetalleReceta.getCantidad(), "cantidad");

		if (recetaRepositorio.buscarPorid(nuevoDetalleReceta.getIdReceta()).isEmpty()) {
			throw new RuntimeException("La receta indicada no existe");
		}
		MateriaPrima materia = materiaRepositorio.buscarPorid(nuevoDetalleReceta.getIdMateria())
				.orElseThrow(() -> new RuntimeException("La materia prima indicada no existe"));

		// la unidad la define la materia prima: si se dejara escribir aparte se
		// podria pedir "2 LITROS" de algo que se mide en gramos
		nuevoDetalleReceta.setUnidad(materia.getUnidadMedida());

		// la misma materia prima dos veces en una receta daria dos cantidades
		// distintas para lo mismo
		boolean repetida = repositorio.buscarPorReceta(nuevoDetalleReceta.getIdReceta()).stream()
				.filter(otro -> nuevoDetalleReceta.getIdDetalle() == null
						|| !nuevoDetalleReceta.getIdDetalle().equals(otro.getIdDetalle()))
				.anyMatch(otro -> nuevoDetalleReceta.getIdMateria().equals(otro.getIdMateria()));
		if (repetida) {
			throw new RuntimeException("Esa materia prima ya está en la receta: edita la cantidad de esa línea");
		}

		return repositorio.guardar(nuevoDetalleReceta);
	}

	@Override
	public DetalleReceta buscarPorId(int idDetalle) {
		return repositorio.buscarPorid(idDetalle)
				.orElseThrow(() -> new RuntimeException("Detalle de receta no encontrado"));
	}

	@Override
	public List<DetalleReceta> listarTodos() {
		return repositorio.listarTodos();
	}

	@Override
	public void eliminar(int idDetalle) {
		buscarPorId(idDetalle);
		repositorio.eliminar(idDetalle);
	}
}
