package com.andiana.api.aplicacion;

import com.andiana.api.dominio.modelo.MateriaPrima;
import com.andiana.api.dominio.puerto.MateriaPrimaRepositorio;

public class MateriasEnMemoria extends RepositorioEnMemoria<MateriaPrima> implements MateriaPrimaRepositorio {

	public MateriasEnMemoria() {
		super(MateriaPrima::getIdMateria, MateriaPrima::setIdMateria);
	}
}
