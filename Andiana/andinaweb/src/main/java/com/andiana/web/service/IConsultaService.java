package com.andiana.web.service;

import java.util.List;

import com.andiana.web.model.dto.response.ConteoRecetaDto;
import com.andiana.web.model.dto.response.MateriaEnRecetaDto;
import com.andiana.web.model.dto.response.OpcionSelectDto;

public interface IConsultaService {

    List<MateriaEnRecetaDto> materiasDeLaReceta(Integer idReceta);

    List<ConteoRecetaDto> conteoDeMateriasPorReceta();

    List<OpcionSelectDto> recetasParaElSelector();
}
