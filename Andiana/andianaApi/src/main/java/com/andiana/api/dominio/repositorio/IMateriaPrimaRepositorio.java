package com.andiana.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.MateriaPrima;

/**
 * Lo que el dominio necesita del almacen de datos. Es una interfaz propia, sin
 * nada de JPA ni de Spring: quien la implementa vive en infraestructura.
 */
public interface IMateriaPrimaRepositorio {

	MateriaPrima guardar(MateriaPrima nuevoMateriaPrima);

	Optional<MateriaPrima> buscarPorid(int idMateriaPrima);

	List<MateriaPrima> listarTodos();

	void eliminar(int idMateriaPrima);
}
