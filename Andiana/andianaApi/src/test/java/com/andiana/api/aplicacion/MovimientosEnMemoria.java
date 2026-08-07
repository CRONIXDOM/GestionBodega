package com.andiana.api.aplicacion;

import java.util.Comparator;
import java.util.List;

import com.andiana.api.dominio.modelo.MovimientoInventario;
import com.andiana.api.dominio.puerto.MovimientoInventarioRepositorio;

/** El falso, pero respetando el orden cronologico que exige el puerto. */
public class MovimientosEnMemoria extends RepositorioEnMemoria<MovimientoInventario>
		implements MovimientoInventarioRepositorio {

	public MovimientosEnMemoria() {
		super(MovimientoInventario::getIdMovimiento, MovimientoInventario::setIdMovimiento);
	}

	@Override
	public List<MovimientoInventario> buscarPorMateriaPrima(Integer idMateriaPrima) {
		return listar().stream()
				.filter(m -> idMateriaPrima.equals(m.getIdMateriaPrima()))
				.sorted(Comparator.comparing(MovimientoInventario::getFecha)
						.thenComparing(MovimientoInventario::getIdMovimiento))
				.toList();
	}
}
