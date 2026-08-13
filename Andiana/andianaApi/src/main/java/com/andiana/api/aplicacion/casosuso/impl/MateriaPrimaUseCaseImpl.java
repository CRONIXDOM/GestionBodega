package com.andiana.api.aplicacion.casosuso.impl;

import java.math.BigDecimal;
import java.util.List;

import com.andiana.api.aplicacion.casosuso.entrada.IMateriaPrimaUseCase;
import com.andiana.api.aplicacion.util.Validaciones;
import com.andiana.api.dominio.entidades.MateriaPrima;
import com.andiana.api.dominio.repositorio.IMateriaPrimaRepositorio;

public class MateriaPrimaUseCaseImpl implements IMateriaPrimaUseCase {

	private final IMateriaPrimaRepositorio repositorio;

	public MateriaPrimaUseCaseImpl(IMateriaPrimaRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public MateriaPrima guardar(MateriaPrima nuevaMateriaPrima) {
		nuevaMateriaPrima.setNombre(Validaciones.normalizar(nuevaMateriaPrima.getNombre()));
		nuevaMateriaPrima.setUnidadMedida(Validaciones.normalizar(nuevaMateriaPrima.getUnidadMedida()));

		Validaciones.obligatorio(nuevaMateriaPrima.getNombre(), "nombre");
		Validaciones.obligatorio(nuevaMateriaPrima.getUnidadMedida(), "unidad de medida");
		Validaciones.noRepetido(repositorio.listarTodos(), MateriaPrima::getIdMateria, MateriaPrima::getNombre,
				nuevaMateriaPrima.getIdMateria(), nuevaMateriaPrima.getNombre(), "una materia prima llamada");

		if (nuevaMateriaPrima.getStockMinimo() == null) {
			nuevaMateriaPrima.setStockMinimo(BigDecimal.ZERO);
		}
		if (nuevaMateriaPrima.getStockMinimo().signum() < 0) {
			throw new RuntimeException("El stock mínimo no puede ser negativo");
		}

		if (nuevaMateriaPrima.getIdMateria() == null) {
			nuevaMateriaPrima.setStockActual(BigDecimal.ZERO);
		} else {
			nuevaMateriaPrima.setStockActual(buscarPorId(nuevaMateriaPrima.getIdMateria()).getStockActual());
		}

		return repositorio.guardar(nuevaMateriaPrima);
	}

	@Override
	public MateriaPrima buscarPorId(int idMateria) {
		return repositorio.buscarPorId(idMateria)
				.orElseThrow(() -> new RuntimeException("Materia prima no encontrada"));
	}

	@Override
	public List<MateriaPrima> listarTodos() {
		return repositorio.listarTodos();
	}

	@Override
	public void eliminar(int idMateria) {
		buscarPorId(idMateria);
		repositorio.eliminar(idMateria);
	}
}
