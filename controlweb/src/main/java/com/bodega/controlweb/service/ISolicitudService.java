package com.bodega.controlweb.service;

import java.util.List;

import com.bodega.controlweb.model.dto.request.SolicitudRequestDto;
import com.bodega.controlweb.model.dto.response.SolicitudResponseDto;

public interface ISolicitudService {

    List<SolicitudResponseDto> listarSolicitud();

    void guardarSolicitud(SolicitudRequestDto nuevo);

    SolicitudResponseDto buscarSolicitudId(Integer id);

    void eliminarSolicitud(Integer id);
}
