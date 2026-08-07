package com.andiana.api.infraestructura.adaptador;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.andiana.api.dominio.modelo.MovimientoInventario;
import com.andiana.api.dominio.puerto.MovimientoInventarioRepositorio;
import com.andiana.api.infraestructura.entidad.MovimientoInventarioEntidad;
import com.andiana.api.infraestructura.jpa.MovimientoInventarioJpaRepositorio;
import com.andiana.api.infraestructura.mapeador.MovimientoInventarioMapeador;

@Repository
public class MovimientoInventarioAdaptador extends AdaptadorCrud<MovimientoInventario, MovimientoInventarioEntidad>
		implements MovimientoInventarioRepositorio {

	private final MovimientoInventarioJpaRepositorio jpaMovimientoInventario;

	public MovimientoInventarioAdaptador(MovimientoInventarioJpaRepositorio jpaMovimientoInventario) {
		super(jpaMovimientoInventario, MovimientoInventarioMapeador::aDominio, MovimientoInventarioMapeador::aEntidad);
		this.jpaMovimientoInventario = jpaMovimientoInventario;
	}

	@Override
	public List<MovimientoInventario> buscarPorMateriaPrima(Integer idMateriaPrima) {
		return convertir(jpaMovimientoInventario.findByIdMateriaPrimaOrderByFechaAscIdMovimientoAsc(idMateriaPrima));
	}
}
