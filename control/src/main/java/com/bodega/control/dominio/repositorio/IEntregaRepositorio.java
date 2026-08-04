package com.bodega.control.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Entrega;

public interface IEntregaRepositorio {
	
	Entrega guardar (Entrega nuevaEntrega);
	
	Optional<Entrega> buscarPorid (int Entrega);
	
	List<Entrega> listarTodos();
	
	void eliminar (int Entrega);

}
