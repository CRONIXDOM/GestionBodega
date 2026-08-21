package com.translog.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.translog.web.model.dto.request.ConductorRequestDto;
import com.translog.web.model.dto.response.ConductorResponseDto;
import com.translog.web.model.dto.response.OpcionSelectDto;
import com.translog.web.service.IConductorService;

@Service
public class ConductorServiceImpl implements IConductorService {

    private final WebClient webCliente;

    public ConductorServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<ConductorResponseDto> listarConductor() {
        return webCliente.get().uri("/conductor").retrieve()
                .bodyToFlux(ConductorResponseDto.class).collectList().block();
    }

    @Override
    public void guardarConductor(ConductorRequestDto nuevo) {
        webCliente.post().uri("/conductor").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public ConductorResponseDto buscarConductorId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/conductor/buscarId/{id}").build(id))
                .retrieve().bodyToMono(ConductorResponseDto.class).block();
    }

    @Override
    public void eliminarConductor(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/conductor/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public List<OpcionSelectDto> listarOpciones() {
        return listarConductor().stream()
                .map(op -> new OpcionSelectDto(op.getIdConductor(), op.getNombre() + " · " + op.getLicencia()))
                .toList();
    }
}
