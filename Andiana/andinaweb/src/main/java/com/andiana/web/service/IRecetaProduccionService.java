package com.andiana.web.service;

import java.util.List;

import com.andiana.web.model.dto.request.RecetaProduccionRequestDto;
import com.andiana.web.model.dto.response.RecetaProduccionResponseDto;
import com.andiana.web.model.dto.response.OpcionSelectDto;

public interface IRecetaProduccionService {

    List<RecetaProduccionResponseDto> listarReceta();

    void guardarReceta(RecetaProduccionRequestDto nuevo);

    RecetaProduccionResponseDto buscarRecetaId(Integer id);

    void eliminarReceta(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
