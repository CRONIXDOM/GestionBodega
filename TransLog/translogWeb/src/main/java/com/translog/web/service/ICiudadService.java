package com.translog.web.service;

import java.util.List;

import com.translog.web.model.dto.request.CiudadRequestDto;
import com.translog.web.model.dto.response.CiudadResponseDto;
import com.translog.web.model.dto.response.OpcionSelectDto;

public interface ICiudadService {

    List<CiudadResponseDto> listarCiudad();

    void guardarCiudad(CiudadRequestDto nuevo);

    CiudadResponseDto buscarCiudadId(Integer id);

    void eliminarCiudad(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
