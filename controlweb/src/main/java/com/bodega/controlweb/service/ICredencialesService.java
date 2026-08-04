package com.bodega.controlweb.service;

import java.util.List;

import com.bodega.controlweb.model.dto.request.CredencialesRequestDto;
import com.bodega.controlweb.model.dto.response.CredencialesResponseDto;

public interface ICredencialesService {

    List<CredencialesResponseDto> listarCredenciales();

    void guardarCredenciales(CredencialesRequestDto nuevo);

    CredencialesResponseDto buscarCredencialesId(Integer id);

    void eliminarCredenciales(Integer id);

    List<CredencialesResponseDto> buscarPorUsuario(String usuario);
}
