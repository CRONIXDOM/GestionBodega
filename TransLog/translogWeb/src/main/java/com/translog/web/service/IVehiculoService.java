package com.translog.web.service;

import java.util.List;

import com.translog.web.model.dto.request.VehiculoRequestDto;
import com.translog.web.model.dto.response.VehiculoResponseDto;
import com.translog.web.model.dto.response.OpcionSelectDto;

public interface IVehiculoService {

    List<VehiculoResponseDto> listarVehiculo();

    void guardarVehiculo(VehiculoRequestDto nuevo);

    VehiculoResponseDto buscarVehiculoId(Integer id);

    void eliminarVehiculo(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
