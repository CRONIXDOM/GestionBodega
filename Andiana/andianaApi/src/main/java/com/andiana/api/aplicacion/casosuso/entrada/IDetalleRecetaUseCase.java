package com.andiana.api.aplicacion.casosuso.entrada;

import java.util.List;

import com.andiana.api.dominio.entidades.DetalleReceta;

public interface IDetalleRecetaUseCase {

	DetalleReceta guardar(DetalleReceta nuevoDetalleReceta);

	DetalleReceta buscarPorId(int idDetalle);

	List<DetalleReceta> listarTodos();

	void eliminar(int idDetalle);

}
