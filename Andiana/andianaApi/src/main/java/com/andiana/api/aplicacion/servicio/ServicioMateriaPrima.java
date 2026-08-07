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
		Validar.unoDe(materia.getUnidadMedida(), "unidad de medida",
				"LITRO", "MILILITRO", "KILOGRAMO", "GRAMO", "UNIDAD");
		Validar.noRepetido(materias.listar(), MateriaPrima::getIdMateriaPrima, MateriaPrima::getNombre,
				materia.getIdMateriaPrima(), materia.getNombre(), "una materia prima llamada");

		// el stock lo mueven los movimientos de inventario, no este formulario:
		// al crearla arranca en cero y al editarla se respeta el que ya tenia.
		if (materia.getIdMateriaPrima() == null) {
			materia.setStock(BigDecimal.ZERO);
		} else {
			materia.setStock(buscarPorId(materia.getIdMateriaPrima()).getStock());
		}
	}
}
