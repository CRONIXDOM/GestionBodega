package com.andiana.web.service;

import java.util.List;

import com.andiana.web.model.dto.request.ControlCalidadRequestDto;
import com.andiana.web.model.dto.response.ControlCalidadResponseDto;
import com.andiana.web.model.dto.response.OpcionSelectDto;

public interface IControlCalidadService {

    List<ControlCalidadResponseDto> listarControlCalidad();

    void guardarControlCalidad(ControlCalidadRequestDto nuevo);

    ControlCalidadResponseDto buscarControlCalidadId(Integer id);

    void eliminarControlCalidad(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
