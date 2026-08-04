package com.bodega.controlweb.service;

import java.util.List;

import com.bodega.controlweb.model.dto.request.TipoRequestDto;
import com.bodega.controlweb.model.dto.response.TipoResponseDto;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

public interface ITipoService {

    List<TipoResponseDto> listarTipo();

    void guardarTipo(TipoRequestDto nuevo);

    TipoResponseDto buscarTipoId(Integer id);

    void eliminarTipo(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
