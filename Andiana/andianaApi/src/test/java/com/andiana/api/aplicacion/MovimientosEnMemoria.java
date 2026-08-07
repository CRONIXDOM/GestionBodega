package com.andiana.api.aplicacion;

import java.util.Comparator;
import java.util.List;

import com.andiana.api.dominio.entidades.MovimientoMateriaPrima;
import com.andiana.api.dominio.repositorio.IMovimientoMateriaPrimaRepositorio;

/** El falso, pero respetando el orden cronologico que exige el puerto. */
public class MovimientosEnMemoria extends RepositorioEnMemoria<MovimientoMateriaPrima>
		implements IMovimientoMateriaPrimaRepositorio {

	public MovimientosEnMemoria() {
		super(MovimientoMateriaPrima::getIdMovimiento, MovimientoMateriaPrima::setIdMovimiento);
	}

	@Override
	public List<MovimientoMateriaPrima> buscarPorMateria(int idMateria) {
		return listarTodos().stream()
				.filter(m -> m.getIdMateria() == idMateria)
				.sorted(Comparator.comparing(MovimientoMateriaPrima::getFecha)
						.thenComparing(MovimientoMateriaPrima::getIdMovimiento))
				.toList();
	}
}
