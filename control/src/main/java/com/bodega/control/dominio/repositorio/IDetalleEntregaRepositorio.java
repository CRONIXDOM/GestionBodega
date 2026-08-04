package com.bodega.control.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.DetalleEntrega;

public interface IDetalleEntregaRepositorio {
	
	DetalleEntrega guardar (DetalleEntrega nuevaDetalleEntrega);
	
	Optional<DetalleEntrega> buscarPorid (int DetalleEntrega);
	
	List<DetalleEntrega> listarTodos();
	
	void eliminar (int DetalleEntrega);

}
