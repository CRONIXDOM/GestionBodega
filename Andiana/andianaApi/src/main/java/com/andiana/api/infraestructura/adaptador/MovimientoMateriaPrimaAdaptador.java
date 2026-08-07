package com.andiana.api.infraestructura.adaptador;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.andiana.api.dominio.modelo.MovimientoMateriaPrima;
import com.andiana.api.dominio.puerto.MovimientoMateriaPrimaRepositorio;
import com.andiana.api.infraestructura.entidad.MovimientoMateriaPrimaEntidad;
import com.andiana.api.infraestructura.jpa.MovimientoMateriaPrimaJpaRepositorio;
import com.andiana.api.infraestructura.mapeador.MovimientoMateriaPrimaMapeador;

@Repository
public class MovimientoMateriaPrimaAdaptador extends AdaptadorCrud<MovimientoMateriaPrima, MovimientoMateriaPrimaEntidad>
		implements MovimientoMateriaPrimaRepositorio {

	private final MovimientoMateriaPrimaJpaRepositorio jpaMovimientoMateriaPrima;

	public MovimientoMateriaPrimaAdaptador(MovimientoMateriaPrimaJpaRepositorio jpaMovimientoMateriaPrima) {
		super(jpaMovimientoMateriaPrima, MovimientoMateriaPrimaMapeador::aDominio, MovimientoMateriaPrimaMapeador::aEntidad);
		this.jpaMovimientoMateriaPrima = jpaMovimientoMateriaPrima;
	}

	@Override
	public List<MovimientoMateriaPrima> buscarPorMateria(Integer idMateria) {
		return convertir(jpaMovimientoMateriaPrima.findByIdMateriaOrderByFechaAscIdMovimientoAsc(idMateria));
	}
}
