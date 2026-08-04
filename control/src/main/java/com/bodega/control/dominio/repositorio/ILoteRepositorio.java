package com.bodega.control.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Lote;

public interface ILoteRepositorio {
	
	Lote guardar (Lote nuevaLote);
	
	Optional<Lote> buscarPorid (int Lote);
	
	List<Lote> listarTodos();
	
	void eliminar (int Lote);

}
