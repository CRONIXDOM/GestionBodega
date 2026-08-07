package com.andiana.api.aplicacion.puerto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Las dos consultas que pidio la gerencia para apoyar la toma de decisiones.
 */
public interface ConsultaRecetas {

	/** Las materias primas que se usan en una receta, con su cantidad. */
	List<MateriaEnReceta> materiasDeLaReceta(Integer idReceta);

	/** Cuantas materias primas lleva cada receta. */
	List<ConteoDeReceta> conteoDeMateriasPorReceta();

	/** Una linea de la primera consulta. */
	record MateriaEnReceta(Integer idMateria, String materiaPrima, BigDecimal cantidad,
			String unidad, BigDecimal stockActual) {
	}

	/** Una linea de la segunda consulta. */
	record ConteoDeReceta(Integer idReceta, String producto, Integer version, boolean activa,
			long totalMateriasPrimas) {
	}
}
