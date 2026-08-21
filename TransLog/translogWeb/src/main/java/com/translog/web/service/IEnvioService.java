package com.translog.web.service;

import java.util.List;

import com.translog.web.model.dto.request.EnvioRequestDto;
import com.translog.web.model.dto.response.EnvioResponseDto;
import com.translog.web.model.dto.response.OpcionSelectDto;

public interface IEnvioService {

    List<EnvioResponseDto> listarEnvio();

    void guardarEnvio(EnvioRequestDto nuevo);

    EnvioResponseDto buscarEnvioId(Integer id);

    void eliminarEnvio(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
