package com.bodega.controlweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bodega.controlweb.model.dto.request.RolRequestDto;
import com.bodega.controlweb.model.dto.response.RolResponseDto;
import com.bodega.controlweb.service.IRolService;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

@Service
public class RolServiceImpl implements IRolService {

    private final WebClient webCliente;

    public RolServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<RolResponseDto> listarRol() {
        return webCliente.get().uri("/rol").retrieve()
                .bodyToFlux(RolResponseDto.class).collectList().block();
    }

    @Override
    public void guardarRol(RolRequestDto nuevo) {
        webCliente.post().uri("/rol").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public RolResponseDto buscarRolId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/rol/buscarId/{id}").build(id))
                .retrieve().bodyToMono(RolResponseDto.class).block();
    }

    @Override
    public void eliminarRol(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/rol/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public List<OpcionSelectDto> listarOpciones() {
        return listarRol().stream()
                .map(op -> new OpcionSelectDto(op.getIdRol(), op.getNombreRol()))
                .toList();
    }
}
