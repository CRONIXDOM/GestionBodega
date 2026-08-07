package com.andiana.web.service;

import java.util.List;

import com.andiana.web.model.dto.request.LoteProduccionRequestDto;
import com.andiana.web.model.dto.response.LoteProduccionResponseDto;
import com.andiana.web.model.dto.response.OpcionSelectDto;

public interface ILoteProduccionService {

    List<LoteProduccionResponseDto> listarLote();

    void guardarLote(LoteProduccionRequestDto nuevo);

    LoteProduccionResponseDto buscarLoteId(Integer id);

    void eliminarLote(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
