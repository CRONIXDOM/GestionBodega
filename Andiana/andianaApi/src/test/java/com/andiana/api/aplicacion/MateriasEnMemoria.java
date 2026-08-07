package com.andiana.api.aplicacion;

import com.andiana.api.dominio.entidades.MateriaPrima;
import com.andiana.api.dominio.repositorio.IMateriaPrimaRepositorio;

public class MateriasEnMemoria extends RepositorioEnMemoria<MateriaPrima> implements IMateriaPrimaRepositorio {

	public MateriasEnMemoria() {
		super(MateriaPrima::getIdMateria, MateriaPrima::setIdMateria);
	}

	@Override
	protected MateriaPrima copiar(MateriaPrima m) {
		return new MateriaPrima(m.getIdMateria(), m.getNombre(), m.getUnidadMedida(),
				m.getStockActual(), m.getStockMinimo());
	}
}
