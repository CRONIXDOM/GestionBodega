package com.bodega.controlweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bodega.controlweb.model.dto.request.SedeRequestDto;
import com.bodega.controlweb.model.dto.response.SedeResponseDto;
import com.bodega.controlweb.service.ISedeService;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

@Service
public class SedeServiceImpl implements ISedeService {

    private final WebClient webCliente;

    public SedeServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<SedeResponseDto> listarSede() {
        return webCliente.get().uri("/sede").retrieve()
                .bodyToFlux(SedeResponseDto.class).collectList().block();
    }

    @Override
    public void guardarSede(SedeRequestDto nuevo) {
        webCliente.post().uri("/sede").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public SedeResponseDto buscarSedeId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/sede/buscarId/{id}").build(id))
                .retrieve().bodyToMono(SedeResponseDto.class).block();
    }

    @Override
    public void eliminarSede(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/sede/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public List<OpcionSelectDto> listarOpciones() {
        return listarSede().stream()
                .map(op -> new OpcionSelectDto(op.getIdSede(), op.getNombreSede()))
                .toList();
    }
}
