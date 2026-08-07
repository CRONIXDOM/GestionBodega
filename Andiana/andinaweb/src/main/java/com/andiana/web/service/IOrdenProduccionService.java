package com.andiana.web.service;

import java.util.List;

import com.andiana.web.model.dto.request.OrdenProduccionRequestDto;
import com.andiana.web.model.dto.response.OrdenProduccionResponseDto;
import com.andiana.web.model.dto.response.OpcionSelectDto;

public interface IOrdenProduccionService {

    List<OrdenProduccionResponseDto> listarOrden();

    void guardarOrden(OrdenProduccionRequestDto nuevo);

    OrdenProduccionResponseDto buscarOrdenId(Integer id);

    void eliminarOrden(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
