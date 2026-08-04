package com.bodega.controlweb.service;

import java.util.List;

import com.bodega.controlweb.model.dto.request.UbicacionRequestDto;
import com.bodega.controlweb.model.dto.response.UbicacionResponseDto;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

public interface IUbicacionService {

    List<UbicacionResponseDto> listarUbicacion();

    void guardarUbicacion(UbicacionRequestDto nuevo);

    UbicacionResponseDto buscarUbicacionId(Integer id);

    void eliminarUbicacion(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
