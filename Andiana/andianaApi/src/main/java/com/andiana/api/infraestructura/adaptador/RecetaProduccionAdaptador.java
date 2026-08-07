package com.andiana.api.infraestructura.adaptador;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.andiana.api.dominio.modelo.RecetaProduccion;
import com.andiana.api.dominio.puerto.RecetaProduccionRepositorio;
import com.andiana.api.infraestructura.entidad.RecetaProduccionEntidad;
import com.andiana.api.infraestructura.jpa.RecetaProduccionJpaRepositorio;
import com.andiana.api.infraestructura.mapeador.RecetaProduccionMapeador;

@Repository
public class RecetaProduccionAdaptador extends AdaptadorCrud<RecetaProduccion, RecetaProduccionEntidad>
		implements RecetaProduccionRepositorio {

	private final RecetaProduccionJpaRepositorio jpaRecetaProduccion;

	public RecetaProduccionAdaptador(RecetaProduccionJpaRepositorio jpaRecetaProduccion) {
		super(jpaRecetaProduccion, RecetaProduccionMapeador::aDominio, RecetaProduccionMapeador::aEntidad);
		this.jpaRecetaProduccion = jpaRecetaProduccion;
	}

	@Override
	public List<RecetaProduccion> buscarPorProducto(Integer idProducto) {
		return convertir(jpaRecetaProduccion.findByIdProductoOrderByVersionAsc(idProducto));
	}
}
