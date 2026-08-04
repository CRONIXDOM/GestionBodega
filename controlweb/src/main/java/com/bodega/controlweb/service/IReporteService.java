package com.bodega.controlweb.service;

import java.util.List;

import com.bodega.controlweb.model.dto.request.ReporteRequestDto;
import com.bodega.controlweb.model.dto.response.ReporteResponseDto;

public interface IReporteService {

    List<ReporteResponseDto> listarReporte();

    void guardarReporte(ReporteRequestDto nuevo);

    ReporteResponseDto buscarReporteId(Integer id);

    void eliminarReporte(Integer id);
}
