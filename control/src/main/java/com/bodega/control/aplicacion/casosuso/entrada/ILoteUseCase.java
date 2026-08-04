package com.bodega.control.aplicacion.casosuso.entrada;

import java.util.List;

import com.bodega.control.dominio.entidades.Lote;

public interface ILoteUseCase {
	
	Lote guardar (Lote nuevaLote);
	
	Lote buscarPorid (int Lote);
	
	List<Lote> listarTodos();
	
	void eliminar (int Lote);

	Lote buscarPorId(int idLote);

}
