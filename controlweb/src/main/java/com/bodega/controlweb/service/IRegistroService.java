package com.bodega.controlweb.service;

import java.util.List;

import com.bodega.controlweb.model.dto.request.RegistroRequestDto;
import com.bodega.controlweb.model.dto.response.RegistroResponseDto;

public interface IRegistroService {

    List<RegistroResponseDto> listarRegistro();

    void guardarRegistro(RegistroRequestDto nuevo);

    RegistroResponseDto buscarRegistroId(Integer id);

    void eliminarRegistro(Integer id);
}
