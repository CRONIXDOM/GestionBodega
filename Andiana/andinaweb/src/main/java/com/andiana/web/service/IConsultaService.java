package com.andiana.web.service;

import java.util.List;

import com.andiana.web.model.dto.response.ConteoRecetaDto;
import com.andiana.web.model.dto.response.MateriaEnRecetaDto;
import com.andiana.web.model.dto.response.OpcionSelectDto;

public interface IConsultaService {

    /** Consulta 1: las materias primas que se usan en una receta. */
    List<MateriaEnRecetaDto> materiasDeLaReceta(Integer idReceta);

    /** Consulta 2: cuántas materias primas lleva cada receta. */
    List<ConteoRecetaDto> conteoDeMateriasPorReceta();

    /** Las recetas nombradas por su producto y versión, para el selector. */
    List<OpcionSelectDto> recetasParaElSelector();
}
