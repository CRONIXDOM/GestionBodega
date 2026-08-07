package com.andiana.web.service;

import java.util.List;

import com.andiana.web.model.dto.request.DetalleRecetaRequestDto;
import com.andiana.web.model.dto.response.DetalleRecetaResponseDto;
import com.andiana.web.model.dto.response.OpcionSelectDto;

public interface IDetalleRecetaService {

    List<DetalleRecetaResponseDto> listarDetalleReceta();

    void guardarDetalleReceta(DetalleRecetaRequestDto nuevo);

    DetalleRecetaResponseDto buscarDetalleRecetaId(Integer id);

    void eliminarDetalleReceta(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
