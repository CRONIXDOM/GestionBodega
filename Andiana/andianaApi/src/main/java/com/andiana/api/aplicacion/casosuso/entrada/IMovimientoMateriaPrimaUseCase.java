package com.andiana.api.aplicacion.casosuso.entrada;

import java.util.List;

import com.andiana.api.dominio.entidades.MovimientoMateriaPrima;

public interface IMovimientoMateriaPrimaUseCase {

	MovimientoMateriaPrima guardar(MovimientoMateriaPrima nuevoMovimientoMateriaPrima);

	MovimientoMateriaPrima buscarPorId(int idMovimiento);

	List<MovimientoMateriaPrima> listarTodos();

	void eliminar(int idMovimiento);

}
