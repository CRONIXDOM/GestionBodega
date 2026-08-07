package com.andiana.api.infraestructura.adaptador;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.andiana.api.dominio.modelo.RecetaDetalle;
import com.andiana.api.dominio.puerto.RecetaDetalleRepositorio;
import com.andiana.api.infraestructura.entidad.RecetaDetalleEntidad;
import com.andiana.api.infraestructura.jpa.RecetaDetalleJpaRepositorio;
import com.andiana.api.infraestructura.mapeador.RecetaDetalleMapeador;

@Repository
public class RecetaDetalleAdaptador extends AdaptadorCrud<RecetaDetalle, RecetaDetalleEntidad>
		implements RecetaDetalleRepositorio {

	private final RecetaDetalleJpaRepositorio jpaRecetaDetalle;

	public RecetaDetalleAdaptador(RecetaDetalleJpaRepositorio jpaRecetaDetalle) {
		super(jpaRecetaDetalle, RecetaDetalleMapeador::aDominio, RecetaDetalleMapeador::aEntidad);
		this.jpaRecetaDetalle = jpaRecetaDetalle;
	}

	@Override
	public List<RecetaDetalle> buscarPorReceta(Integer idReceta) {
		return convertir(jpaRecetaDetalle.findByIdReceta(idReceta));
	}
}
