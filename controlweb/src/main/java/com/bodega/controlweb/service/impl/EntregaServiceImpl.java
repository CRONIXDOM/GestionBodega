package com.bodega.controlweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bodega.controlweb.model.dto.request.EntregaRequestDto;
import com.bodega.controlweb.model.dto.response.EntregaResponseDto;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;
import com.bodega.controlweb.service.IEntregaService;

@Service
public class EntregaServiceImpl implements IEntregaService {

    private final WebClient webCliente;

    public EntregaServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<EntregaResponseDto> listarEntrega() {
        return webCliente.get().uri("/entrega").retrieve()
                .bodyToFlux(EntregaResponseDto.class).collectList().block();
    }

    @Override
    public EntregaResponseDto guardarEntrega(EntregaRequestDto nuevo) {
        return webCliente.post().uri("/entrega").bodyValue(nuevo).retrieve()
                .bodyToMono(EntregaResponseDto.class).block();
    }

    @Override
    public EntregaResponseDto buscarEntregaId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/entrega/buscarId/{id}").build(id))
                .retrieve().bodyToMono(EntregaResponseDto.class).block();
    }

    @Override
    public void eliminarEntrega(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/entrega/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public List<OpcionSelectDto> listarOpciones() {
        return listarEntrega().stream()
                .map(e -> new OpcionSelectDto(e.getIdEntrega(),
                        "Entrega " + e.getFechaEntrega() + " — " + e.getResponsableEntrega()))
                .toList();
    }
}
