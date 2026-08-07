package com.andiana.api.infraestructura.adaptador;

import org.springframework.stereotype.Repository;

import com.andiana.api.dominio.modelo.MateriaPrima;
import com.andiana.api.dominio.puerto.MateriaPrimaRepositorio;
import com.andiana.api.infraestructura.entidad.MateriaPrimaEntidad;
import com.andiana.api.infraestructura.jpa.MateriaPrimaJpaRepositorio;
import com.andiana.api.infraestructura.mapeador.MateriaPrimaMapeador;

@Repository
public class MateriaPrimaAdaptador extends AdaptadorCrud<MateriaPrima, MateriaPrimaEntidad>
		implements MateriaPrimaRepositorio {

	public MateriaPrimaAdaptador(MateriaPrimaJpaRepositorio jpaMateriaPrima) {
		super(jpaMateriaPrima, MateriaPrimaMapeador::aDominio, MateriaPrimaMapeador::aEntidad);
	}
}
