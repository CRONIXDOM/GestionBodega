package com.andiana.api.aplicacion.casosuso.entrada;

import java.util.List;

import com.andiana.api.dominio.entidades.MateriaPrima;

public interface IMateriaPrimaUseCase {

	MateriaPrima guardar(MateriaPrima nuevoMateriaPrima);

	MateriaPrima buscarPorId(int idMateria);

	List<MateriaPrima> listarTodos();

	void eliminar(int idMateria);

}
