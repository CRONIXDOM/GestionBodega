package com.bodega.controlweb.service;

import java.util.List;

import com.bodega.controlweb.model.dto.request.LoteRequestDto;
import com.bodega.controlweb.model.dto.response.LoteResponseDto;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

public interface ILoteService {

    List<LoteResponseDto> listarLote();

    void guardarLote(LoteRequestDto nuevo);

    LoteResponseDto buscarLoteId(Integer id);

    void eliminarLote(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
