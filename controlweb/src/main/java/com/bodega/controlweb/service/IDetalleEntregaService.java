package com.bodega.controlweb.service;

import java.util.List;

import com.bodega.controlweb.model.dto.request.DetalleEntregaRequestDto;
import com.bodega.controlweb.model.dto.response.DetalleEntregaResponseDto;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

public interface IDetalleEntregaService {

    List<DetalleEntregaResponseDto> listarDetalleEntrega();

    DetalleEntregaResponseDto guardarDetalleEntrega(DetalleEntregaRequestDto nuevo);

    DetalleEntregaResponseDto buscarDetalleEntregaId(Integer id);

    void eliminarDetalleEntrega(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
