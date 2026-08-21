package com.translog.web.service;

import java.util.List;

import com.translog.web.model.dto.request.ConductorRequestDto;
import com.translog.web.model.dto.response.ConductorResponseDto;
import com.translog.web.model.dto.response.OpcionSelectDto;

public interface IConductorService {

    List<ConductorResponseDto> listarConductor();

    void guardarConductor(ConductorRequestDto nuevo);

    ConductorResponseDto buscarConductorId(Integer id);

    void eliminarConductor(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
