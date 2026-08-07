package com.andiana.api.dominio.puerto;

import java.util.List;

import com.andiana.api.dominio.modelo.ControlCalidad;

public interface ControlCalidadRepositorio extends Repositorio<ControlCalidad> {

	/** Los controles hechos a un lote, del mas reciente al mas antiguo. */
	List<ControlCalidad> buscarPorLote(Integer idLote);
}
