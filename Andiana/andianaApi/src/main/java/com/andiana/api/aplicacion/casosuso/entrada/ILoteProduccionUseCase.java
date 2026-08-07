package com.andiana.api.aplicacion.casosuso.entrada;

import java.util.List;

import com.andiana.api.dominio.entidades.LoteProduccion;

public interface ILoteProduccionUseCase {

	LoteProduccion guardar(LoteProduccion nuevoLoteProduccion);

	LoteProduccion buscarPorId(int idLote);

	List<LoteProduccion> listarTodos();

	void eliminar(int idLote);

}
