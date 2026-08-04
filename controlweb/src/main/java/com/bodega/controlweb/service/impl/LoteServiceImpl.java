package com.bodega.controlweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bodega.controlweb.model.dto.request.LoteRequestDto;
import com.bodega.controlweb.model.dto.response.LoteResponseDto;
import com.bodega.controlweb.service.ILoteService;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

@Service
public class LoteServiceImpl implements ILoteService {

    private final WebClient webCliente;

    public LoteServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<LoteResponseDto> listarLote() {
        return webCliente.get().uri("/lote").retrieve()
                .bodyToFlux(LoteResponseDto.class).collectList().block();
    }

    @Override
    public void guardarLote(LoteRequestDto nuevo) {
        webCliente.post().uri("/lote").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public LoteResponseDto buscarLoteId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/lote/buscarId/{id}").build(id))
                .retrieve().bodyToMono(LoteResponseDto.class).block();
    }

    @Override
    public void eliminarLote(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/lote/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public List<OpcionSelectDto> listarOpciones() {
        return listarLote().stream()
                .map(op -> new OpcionSelectDto(op.getIdLote(), "Lote " + op.getNumeroLote() + " — vence " + op.getFechaVencimiento()))
                .toList();
    }
}
