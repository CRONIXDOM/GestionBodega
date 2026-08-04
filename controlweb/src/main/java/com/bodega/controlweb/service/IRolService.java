package com.bodega.controlweb.service;

import java.util.List;

import com.bodega.controlweb.model.dto.request.RolRequestDto;
import com.bodega.controlweb.model.dto.response.RolResponseDto;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

public interface IRolService {

    List<RolResponseDto> listarRol();

    void guardarRol(RolRequestDto nuevo);

    RolResponseDto buscarRolId(Integer id);

    void eliminarRol(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
