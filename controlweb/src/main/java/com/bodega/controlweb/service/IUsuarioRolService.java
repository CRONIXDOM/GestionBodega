package com.bodega.controlweb.service;

import java.util.List;

import com.bodega.controlweb.model.dto.request.UsuarioRolRequestDto;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;
import com.bodega.controlweb.model.dto.response.UsuarioRolResponseDto;

public interface IUsuarioRolService {

    List<UsuarioRolResponseDto> listarUsuarioRol();

    void guardarUsuarioRol(UsuarioRolRequestDto nuevo);

    UsuarioRolResponseDto buscarUsuarioRolId(Integer id);

    void eliminarUsuarioRol(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
