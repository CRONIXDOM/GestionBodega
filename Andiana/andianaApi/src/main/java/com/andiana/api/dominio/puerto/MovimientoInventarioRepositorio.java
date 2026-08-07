package com.andiana.api.dominio.puerto;

import java.util.List;

import com.andiana.api.dominio.modelo.MovimientoInventario;

public interface MovimientoInventarioRepositorio extends Repositorio<MovimientoInventario> {

	/**
	 * El historial de una materia prima en orden cronologico. El orden importa:
	 * un AJUSTE fija el stock en un valor absoluto, asi que para recalcularlo hay
	 * que recorrer los movimientos del mas antiguo al mas reciente.
	 */
	List<MovimientoInventario> buscarPorMateriaPrima(Integer idMateriaPrima);
}
