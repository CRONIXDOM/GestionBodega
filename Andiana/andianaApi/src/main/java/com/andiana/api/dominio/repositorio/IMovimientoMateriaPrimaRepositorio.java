package com.andiana.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.MovimientoMateriaPrima;


public interface IMovimientoMateriaPrimaRepositorio {

	MovimientoMateriaPrima guardar(MovimientoMateriaPrima nuevoMovimientoMateriaPrima);

	Optional<MovimientoMateriaPrima> buscarPorid(int idMovimientoMateriaPrima);

	List<MovimientoMateriaPrima> listarTodos();

	void eliminar(int idMovimientoMateriaPrima);

	List<MovimientoMateriaPrima> buscarPorMateria(int idMateria);
}
