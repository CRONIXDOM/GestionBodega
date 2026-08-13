package com.andiana.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.MateriaPrima;


public interface IMateriaPrimaRepositorio {

	MateriaPrima guardar(MateriaPrima nuevoMateriaPrima);

	Optional<MateriaPrima> buscarPorid(int idMateriaPrima);

	List<MateriaPrima> listarTodos();

	void eliminar(int idMateriaPrima);
}
