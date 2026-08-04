package com.bodega.controlweb.service;

import java.time.LocalDate;
import java.util.List;

import com.bodega.controlweb.model.dto.response.MovimientoReporteResponseDto;

public interface IReporteService {

    List<MovimientoReporteResponseDto> buscarMovimientos(LocalDate desde, LocalDate hasta, Integer idTipo,
            Integer idSede);
}
