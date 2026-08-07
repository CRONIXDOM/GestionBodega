package com.andiana.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.ControlCalidad;

/**
 * Lo que el dominio necesita del almacen de datos. Es una interfaz propia, sin
 * nada de JPA ni de Spring: quien la implementa vive en infraestructura.
 */
public interface IControlCalidadRepositorio {

	ControlCalidad guardar(ControlCalidad nuevoControlCalidad);

	Optional<ControlCalidad> buscarPorid(int idControlCalidad);

	List<ControlCalidad> listarTodos();

	void eliminar(int idControlCalidad);

	/** Los controles hechos a un lote, del mas reciente al mas antiguo. */
	List<ControlCalidad> buscarPorLote(int idLote);
}
