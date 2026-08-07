package com.andiana.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.MovimientoMateriaPrima;

/**
 * Lo que el dominio necesita del almacen de datos. Es una interfaz propia, sin
 * nada de JPA ni de Spring: quien la implementa vive en infraestructura.
 */
public interface IMovimientoMateriaPrimaRepositorio {

	MovimientoMateriaPrima guardar(MovimientoMateriaPrima nuevoMovimientoMateriaPrima);

	Optional<MovimientoMateriaPrima> buscarPorid(int idMovimientoMateriaPrima);

	List<MovimientoMateriaPrima> listarTodos();

	void eliminar(int idMovimientoMateriaPrima);

	/**
	 * El historial de una materia prima en orden cronologico. El orden importa:
	 * un AJUSTE fija el stock en un valor absoluto, asi que para recalcularlo hay
	 * que recorrer los movimientos del mas antiguo al mas reciente.
	 */
	List<MovimientoMateriaPrima> buscarPorMateria(int idMateria);
}
