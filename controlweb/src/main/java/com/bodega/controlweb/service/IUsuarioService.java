package com.bodega.controlweb.service;

import java.util.List;

import com.bodega.controlweb.model.dto.request.UsuarioRequestDto;
import com.bodega.controlweb.model.dto.response.UsuarioResponseDto;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

public interface IUsuarioService {

    List<UsuarioResponseDto> listarUsuario();

    void guardarUsuario(UsuarioRequestDto nuevo);

    UsuarioResponseDto buscarUsuarioId(Integer id);

    void eliminarUsuario(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
