package com.bodega.control.aplicacion.casosuso.entrada;

import java.util.List;

import com.bodega.control.dominio.entidades.Entrega;

public interface IEntregaUseCase {
	
	Entrega guardar (Entrega nuevaEntrega);
	
	Entrega buscarPorid (int Entrega);
	
	List<Entrega> listarTodos();
	
	void eliminar (int Entrega);

	Entrega buscarPorId(int idEntrega);

}
