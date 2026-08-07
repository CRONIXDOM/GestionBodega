package com.andiana.api.infraestructura.adaptador;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.andiana.api.dominio.modelo.Receta;
import com.andiana.api.dominio.puerto.RecetaRepositorio;
import com.andiana.api.infraestructura.entidad.RecetaEntidad;
import com.andiana.api.infraestructura.jpa.RecetaJpaRepositorio;
import com.andiana.api.infraestructura.mapeador.RecetaMapeador;

@Repository
public class RecetaAdaptador extends AdaptadorCrud<Receta, RecetaEntidad>
		implements RecetaRepositorio {

	private final RecetaJpaRepositorio jpaReceta;

	public RecetaAdaptador(RecetaJpaRepositorio jpaReceta) {
		super(jpaReceta, RecetaMapeador::aDominio, RecetaMapeador::aEntidad);
		this.jpaReceta = jpaReceta;
	}

	@Override
	public List<Receta> buscarPorProducto(Integer idProducto) {
		return convertir(jpaReceta.findByIdProductoOrderByFechaAsc(idProducto));
	}
}
