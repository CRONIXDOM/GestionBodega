package com.bodega.controlweb.service;

import java.util.List;

import com.bodega.controlweb.model.dto.request.ZonaRequestDto;
import com.bodega.controlweb.model.dto.response.ZonaResponseDto;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

public interface IZonaService {

    List<ZonaResponseDto> listarZona();

    void guardarZona(ZonaRequestDto nuevo);

    ZonaResponseDto buscarZonaId(Integer id);

    void eliminarZona(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
