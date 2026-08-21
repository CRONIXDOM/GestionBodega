package com.translog.web.service;

import java.util.List;

import com.translog.web.model.dto.request.RutaRequestDto;
import com.translog.web.model.dto.response.RutaResponseDto;
import com.translog.web.model.dto.response.OpcionSelectDto;

public interface IRutaService {

    List<RutaResponseDto> listarRuta();

    void guardarRuta(RutaRequestDto nuevo);

    RutaResponseDto buscarRutaId(Integer id);

    void eliminarRuta(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
