package com.bodega.controlweb.service;

import java.util.List;

import com.bodega.controlweb.model.dto.request.SedeRequestDto;
import com.bodega.controlweb.model.dto.response.SedeResponseDto;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

public interface ISedeService {

    List<SedeResponseDto> listarSede();

    SedeResponseDto guardarSede(SedeRequestDto nuevo);

    SedeResponseDto buscarSedeId(Integer id);

    void eliminarSede(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
