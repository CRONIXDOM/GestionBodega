package com.bodega.control.aplicacion.casosuso.entrada;

import java.util.List;

import com.bodega.control.dominio.entidades.DetalleEntrega;

public interface IDetalleEntregaUseCase {
	
	DetalleEntrega guardar(DetalleEntrega nuevaDetalleEntrega);
	
	DetalleEntrega buscarPorid (int idDetalleEntrega);
	
	List<DetalleEntrega> listarTodos();
	
	void eliminar (int idDetalleEntrega);

}
