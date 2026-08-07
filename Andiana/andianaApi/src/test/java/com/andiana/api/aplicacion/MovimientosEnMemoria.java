package com.andiana.api.aplicacion;

import java.util.Comparator;
import java.util.List;

import com.andiana.api.dominio.modelo.MovimientoMateriaPrima;
import com.andiana.api.dominio.puerto.MovimientoMateriaPrimaRepositorio;

/** El falso, pero respetando el orden cronologico que exige el puerto. */
public class MovimientosEnMemoria extends RepositorioEnMemoria<MovimientoMateriaPrima>
		implements MovimientoMateriaPrimaRepositorio {

	public MovimientosEnMemoria() {
		super(MovimientoMateriaPrima::getIdMovimiento, MovimientoMateriaPrima::setIdMovimiento);
	}

	@Override
	public List<MovimientoMateriaPrima> buscarPorMateria(Integer idMateria) {
		return listar().stream()
				.filter(m -> idMateria.equals(m.getIdMateria()))
				.sorted(Comparator.comparing(MovimientoMateriaPrima::getFecha)
						.thenComparing(MovimientoMateriaPrima::getIdMovimiento))
				.toList();
	}
}
