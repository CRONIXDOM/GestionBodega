package com.bodega.controlweb.service;

import java.util.List;

import com.bodega.controlweb.model.dto.request.DetalleSolicitudRequestDto;
import com.bodega.controlweb.model.dto.response.DetalleSolicitudResponseDto;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

public interface IDetalleSolicitudService {

    List<DetalleSolicitudResponseDto> listarDetalleSolicitud();

    void guardarDetalleSolicitud(DetalleSolicitudRequestDto nuevo);

    DetalleSolicitudResponseDto buscarDetalleSolicitudId(Integer id);

    void eliminarDetalleSolicitud(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
