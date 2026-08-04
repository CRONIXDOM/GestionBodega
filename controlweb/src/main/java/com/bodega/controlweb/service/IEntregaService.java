package com.bodega.controlweb.service;

import java.util.List;

import com.bodega.controlweb.model.dto.request.EntregaRequestDto;
import com.bodega.controlweb.model.dto.response.EntregaResponseDto;

public interface IEntregaService {

    List<EntregaResponseDto> listarEntrega();

    void guardarEntrega(EntregaRequestDto nuevo);

    EntregaResponseDto buscarEntregaId(Integer id);

    void eliminarEntrega(Integer id);
}
