package com.andiana.api.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.LoteProduccion;

public interface ILoteProduccionRepositorio {

	LoteProduccion guardar(LoteProduccion nuevoLoteProduccion);

	Optional<LoteProduccion> buscarPorId(int idLoteProduccion);

	List<LoteProduccion> listarTodos();

	void eliminar(int idLoteProduccion);

	List<LoteProduccion> buscarPorOrden(int idOrden);
}
