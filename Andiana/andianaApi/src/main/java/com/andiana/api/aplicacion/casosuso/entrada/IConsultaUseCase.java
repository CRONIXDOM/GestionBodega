package com.andiana.api.aplicacion.casosuso.entrada;

import java.util.List;

import com.andiana.api.presentacion.dto.response.ConteoRecetaDto;
import com.andiana.api.presentacion.dto.response.MateriaEnRecetaDto;

public interface IConsultaUseCase {

	List<MateriaEnRecetaDto> materiasDeLaReceta(int idReceta);

	List<ConteoRecetaDto> conteoDeMateriasPorReceta();

}
