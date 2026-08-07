package com.andiana.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.LoteProduccion;

/**
 * Lo que el dominio necesita del almacen de datos. Es una interfaz propia, sin
 * nada de JPA ni de Spring: quien la implementa vive en infraestructura.
 */
public interface ILoteProduccionRepositorio {

	LoteProduccion guardar(LoteProduccion nuevoLoteProduccion);

	Optional<LoteProduccion> buscarPorid(int idLoteProduccion);

	List<LoteProduccion> listarTodos();

	void eliminar(int idLoteProduccion);

	/** Los lotes que salieron de una orden. */
	List<LoteProduccion> buscarPorOrden(int idOrden);
}
