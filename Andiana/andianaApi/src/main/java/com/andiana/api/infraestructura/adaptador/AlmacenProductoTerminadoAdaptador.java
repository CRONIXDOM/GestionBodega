package com.andiana.api.infraestructura.adaptador;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.andiana.api.dominio.modelo.AlmacenProductoTerminado;
import com.andiana.api.dominio.puerto.AlmacenProductoTerminadoRepositorio;
import com.andiana.api.infraestructura.entidad.AlmacenProductoTerminadoEntidad;
import com.andiana.api.infraestructura.jpa.AlmacenProductoTerminadoJpaRepositorio;
import com.andiana.api.infraestructura.mapeador.AlmacenProductoTerminadoMapeador;

@Repository
public class AlmacenProductoTerminadoAdaptador extends AdaptadorCrud<AlmacenProductoTerminado, AlmacenProductoTerminadoEntidad>
		implements AlmacenProductoTerminadoRepositorio {

	private final AlmacenProductoTerminadoJpaRepositorio jpaAlmacenProductoTerminado;

	public AlmacenProductoTerminadoAdaptador(AlmacenProductoTerminadoJpaRepositorio jpaAlmacenProductoTerminado) {
		super(jpaAlmacenProductoTerminado, AlmacenProductoTerminadoMapeador::aDominio, AlmacenProductoTerminadoMapeador::aEntidad);
		this.jpaAlmacenProductoTerminado = jpaAlmacenProductoTerminado;
	}

	@Override
	public Optional<AlmacenProductoTerminado> buscarPorLote(Integer idLote) {
		return jpaAlmacenProductoTerminado.findByIdLote(idLote).map(AlmacenProductoTerminadoMapeador::aDominio);
	}
}
