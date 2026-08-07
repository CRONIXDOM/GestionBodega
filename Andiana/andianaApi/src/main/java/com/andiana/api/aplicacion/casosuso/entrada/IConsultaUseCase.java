package com.andiana.api.aplicacion.casosuso.entrada;

import java.util.List;

import com.andiana.api.presentacion.dto.response.ConteoRecetaDto;
import com.andiana.api.presentacion.dto.response.MateriaEnRecetaDto;

/** Las dos consultas que pidio la gerencia para apoyar la toma de decisiones. */
public interface IConsultaUseCase {

	/** Las materias primas que se usan en una receta, con su cantidad. */
	List<MateriaEnRecetaDto> materiasDeLaReceta(int idReceta);

	/** Cuantas materias primas lleva cada receta. */
	List<ConteoRecetaDto> conteoDeMateriasPorReceta();

}
