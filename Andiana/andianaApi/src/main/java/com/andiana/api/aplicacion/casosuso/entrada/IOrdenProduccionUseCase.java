package com.andiana.api.aplicacion.casosuso.entrada;

import java.util.List;

import com.andiana.api.dominio.entidades.OrdenProduccion;

public interface IOrdenProduccionUseCase {

	OrdenProduccion guardar(OrdenProduccion nuevoOrdenProduccion);

	OrdenProduccion buscarPorId(int idOrden);

	List<OrdenProduccion> listarTodos();

	void eliminar(int idOrden);

}
