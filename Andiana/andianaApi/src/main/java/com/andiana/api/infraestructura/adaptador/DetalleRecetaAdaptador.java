package com.andiana.api.infraestructura.adaptador;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.andiana.api.dominio.modelo.DetalleReceta;
import com.andiana.api.dominio.puerto.DetalleRecetaRepositorio;
import com.andiana.api.infraestructura.entidad.DetalleRecetaEntidad;
import com.andiana.api.infraestructura.jpa.DetalleRecetaJpaRepositorio;
import com.andiana.api.infraestructura.mapeador.DetalleRecetaMapeador;

@Repository
public class DetalleRecetaAdaptador extends AdaptadorCrud<DetalleReceta, DetalleRecetaEntidad>
		implements DetalleRecetaRepositorio {

	private final DetalleRecetaJpaRepositorio jpaDetalleReceta;

	public DetalleRecetaAdaptador(DetalleRecetaJpaRepositorio jpaDetalleReceta) {
		super(jpaDetalleReceta, DetalleRecetaMapeador::aDominio, DetalleRecetaMapeador::aEntidad);
		this.jpaDetalleReceta = jpaDetalleReceta;
	}

	@Override
	public List<DetalleReceta> buscarPorReceta(Integer idReceta) {
		return convertir(jpaDetalleReceta.findByIdReceta(idReceta));
	}
}
