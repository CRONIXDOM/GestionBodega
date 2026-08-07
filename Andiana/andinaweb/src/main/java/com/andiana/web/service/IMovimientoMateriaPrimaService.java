package com.andiana.web.service;

import java.util.List;

import com.andiana.web.model.dto.request.MovimientoMateriaPrimaRequestDto;
import com.andiana.web.model.dto.response.MovimientoMateriaPrimaResponseDto;
import com.andiana.web.model.dto.response.OpcionSelectDto;

public interface IMovimientoMateriaPrimaService {

    List<MovimientoMateriaPrimaResponseDto> listarMovimiento();

    void guardarMovimiento(MovimientoMateriaPrimaRequestDto nuevo);

    MovimientoMateriaPrimaResponseDto buscarMovimientoId(Integer id);

    void eliminarMovimiento(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
