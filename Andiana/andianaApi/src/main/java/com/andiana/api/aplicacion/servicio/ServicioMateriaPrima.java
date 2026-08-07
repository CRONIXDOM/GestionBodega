package com.andiana.api.aplicacion.servicio;

import java.math.BigDecimal;

import com.andiana.api.dominio.Validar;
import com.andiana.api.dominio.modelo.MateriaPrima;
import com.andiana.api.dominio.puerto.MateriaPrimaRepositorio;

public class ServicioMateriaPrima extends ServicioCrud<MateriaPrima> {

	private final MateriaPrimaRepositorio materias;

	public ServicioMateriaPrima(MateriaPrimaRepositorio materias) {
		super(materias, "Materia prima");
		this.materias = materias;
	}

	@Override
	protected void validar(MateriaPrima materia) {
		materia.setNombre(Validar.normalizar(materia.getNombre()));
		materia.setUnidadMedida(Validar.normalizar(materia.getUnidadMedida()));

		Validar.obligatorio(materia.getNombre(), "nombre");
		Validar.obligatorio(materia.getUnidadMedida(), "unidad de medida");
		Validar.noRepetido(materias.listar(), MateriaPrima::getIdMateria, MateriaPrima::getNombre,
				materia.getIdMateria(), materia.getNombre(), "una materia prima llamada");

		if (materia.getStockMinimo() == null) {
			materia.setStockMinimo(BigDecimal.ZERO);
		}
		if (materia.getStockMinimo().signum() < 0) {
			throw new com.andiana.api.dominio.ReglaNegocioException("El stock minimo no puede ser negativo");
		}

		// el stock actual lo mueven los movimientos, no este formulario: al crearla
		// arranca en cero y al editarla se respeta el que ya tenia
		if (materia.getIdMateria() == null) {
			materia.setStockActual(BigDecimal.ZERO);
		} else {
			materia.setStockActual(buscarPorId(materia.getIdMateria()).getStockActual());
		}
	}
}
