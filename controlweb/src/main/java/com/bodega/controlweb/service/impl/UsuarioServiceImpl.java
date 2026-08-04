package com.bodega.controlweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bodega.controlweb.model.dto.request.UsuarioRequestDto;
import com.bodega.controlweb.model.dto.response.UsuarioResponseDto;
import com.bodega.controlweb.service.IUsuarioService;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private final WebClient webCliente;

    public UsuarioServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<UsuarioResponseDto> listarUsuario() {
        return webCliente.get().uri("/usuario").retrieve()
                .bodyToFlux(UsuarioResponseDto.class).collectList().block();
    }

    @Override
    public UsuarioResponseDto guardarUsuario(UsuarioRequestDto nuevo) {
        return webCliente.post().uri("/usuario").bodyValue(nuevo)
                .retrieve().bodyToMono(UsuarioResponseDto.class).block();
    }

    @Override
    public UsuarioResponseDto buscarUsuarioId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/usuario/buscarId/{id}").build(id))
                .retrieve().bodyToMono(UsuarioResponseDto.class).block();
    }

    @Override
    public void eliminarUsuario(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/usuario/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public List<OpcionSelectDto> listarOpciones() {
        return listarUsuario().stream()
                .map(op -> new OpcionSelectDto(op.getIdUsuario(), op.getNombreUsuario() + " " + op.getApellidoUsuario()))
                .toList();
    }
}
