package com.bodega.controlweb.service;

import java.util.List;

import com.bodega.controlweb.model.dto.request.EntregaRequestDto;
import com.bodega.controlweb.model.dto.response.EntregaResponseDto;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

public interface IEntregaService {

    List<EntregaResponseDto> listarEntrega();

    EntregaResponseDto guardarEntrega(EntregaRequestDto nuevo);

    EntregaResponseDto buscarEntregaId(Integer id);

    void eliminarEntrega(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
