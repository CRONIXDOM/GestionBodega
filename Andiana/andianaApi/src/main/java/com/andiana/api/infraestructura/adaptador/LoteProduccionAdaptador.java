package com.andiana.api.infraestructura.adaptador;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.andiana.api.dominio.modelo.LoteProduccion;
import com.andiana.api.dominio.puerto.LoteProduccionRepositorio;
import com.andiana.api.infraestructura.entidad.LoteProduccionEntidad;
import com.andiana.api.infraestructura.jpa.LoteProduccionJpaRepositorio;
import com.andiana.api.infraestructura.mapeador.LoteProduccionMapeador;

@Repository
public class LoteProduccionAdaptador extends AdaptadorCrud<LoteProduccion, LoteProduccionEntidad>
		implements LoteProduccionRepositorio {

	private final LoteProduccionJpaRepositorio jpaLoteProduccion;

	public LoteProduccionAdaptador(LoteProduccionJpaRepositorio jpaLoteProduccion) {
		super(jpaLoteProduccion, LoteProduccionMapeador::aDominio, LoteProduccionMapeador::aEntidad);
		this.jpaLoteProduccion = jpaLoteProduccion;
	}

	@Override
	public List<LoteProduccion> buscarPorOrden(Integer idOrden) {
		return convertir(jpaLoteProduccion.findByIdOrden(idOrden));
	}
}
