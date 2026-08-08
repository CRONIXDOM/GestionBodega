package com.andiana.api.aplicacion.casosuso.entrada;

import java.util.List;

import com.andiana.api.dominio.entidades.DetalleReceta;

public interface IDetalleRecetaUseCase {

	DetalleReceta guardar(DetalleReceta nuevoDetalleReceta);

	/** Carga de una sola vez todas las materias primas de una receta. */
	List<DetalleReceta> guardarVarias(List<DetalleReceta> lineas);

	DetalleReceta buscarPorId(int idDetalle);

	List<DetalleReceta> listarTodos();

	void eliminar(int idDetalle);

}
