package com.andiana.api.infraestructura.adaptador;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.andiana.api.dominio.modelo.ControlCalidad;
import com.andiana.api.dominio.puerto.ControlCalidadRepositorio;
import com.andiana.api.infraestructura.entidad.ControlCalidadEntidad;
import com.andiana.api.infraestructura.jpa.ControlCalidadJpaRepositorio;
import com.andiana.api.infraestructura.mapeador.ControlCalidadMapeador;

@Repository
public class ControlCalidadAdaptador extends AdaptadorCrud<ControlCalidad, ControlCalidadEntidad>
		implements ControlCalidadRepositorio {

	private final ControlCalidadJpaRepositorio jpaControlCalidad;

	public ControlCalidadAdaptador(ControlCalidadJpaRepositorio jpaControlCalidad) {
		super(jpaControlCalidad, ControlCalidadMapeador::aDominio, ControlCalidadMapeador::aEntidad);
		this.jpaControlCalidad = jpaControlCalidad;
	}

	@Override
	public Optional<ControlCalidad> buscarPorLote(Integer idLote) {
		return jpaControlCalidad.findByIdLote(idLote).map(ControlCalidadMapeador::aDominio);
	}
}
