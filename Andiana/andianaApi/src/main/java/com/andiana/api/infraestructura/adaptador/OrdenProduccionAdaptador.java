package com.andiana.api.infraestructura.adaptador;

import org.springframework.stereotype.Repository;

import com.andiana.api.dominio.modelo.OrdenProduccion;
import com.andiana.api.dominio.puerto.OrdenProduccionRepositorio;
import com.andiana.api.infraestructura.entidad.OrdenProduccionEntidad;
import com.andiana.api.infraestructura.jpa.OrdenProduccionJpaRepositorio;
import com.andiana.api.infraestructura.mapeador.OrdenProduccionMapeador;

@Repository
public class OrdenProduccionAdaptador extends AdaptadorCrud<OrdenProduccion, OrdenProduccionEntidad>
		implements OrdenProduccionRepositorio {

	public OrdenProduccionAdaptador(OrdenProduccionJpaRepositorio jpaOrdenProduccion) {
		super(jpaOrdenProduccion, OrdenProduccionMapeador::aDominio, OrdenProduccionMapeador::aEntidad);
	}
}
