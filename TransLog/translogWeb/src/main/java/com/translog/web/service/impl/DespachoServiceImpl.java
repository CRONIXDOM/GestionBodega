package com.translog.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.translog.web.model.dto.request.DespachoRequestDto;
import com.translog.web.model.dto.response.DespachoResponseDto;
import com.translog.web.model.dto.response.EnvioResponseDto;
import com.translog.web.model.dto.response.OpcionSelectDto;
import com.translog.web.service.IDespachoService;

@Service
public class DespachoServiceImpl implements IDespachoService {

    private final WebClient webCliente;

    public DespachoServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<DespachoResponseDto> listarDespacho() {
        return webCliente.get().uri("/despacho").retrieve()
                .bodyToFlux(DespachoResponseDto.class).collectList().block();
    }

    @Override
    public void guardarDespacho(DespachoRequestDto nuevo) {
        webCliente.post().uri("/despacho").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public DespachoResponseDto buscarDespachoId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/despacho/buscarId/{id}").build(id))
                .retrieve().bodyToMono(DespachoResponseDto.class).block();
    }

    @Override
    public void eliminarDespacho(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/despacho/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public List<OpcionSelectDto> listarOpciones() {
        return listarDespacho().stream()
                .map(op -> new OpcionSelectDto(op.getIdDespacho(), "Despacho " + op.getIdDespacho()))
                .toList();
    }

    @Override
    public List<EnvioResponseDto> enviosDelDespacho(Integer idDespacho) {
        return webCliente.get().uri(ub -> ub.path("/despacho/{id}/envios").build(idDespacho))
                .retrieve().bodyToFlux(EnvioResponseDto.class).collectList().block();
    }

    @Override
    public List<EnvioResponseDto> enviosDisponibles(Integer idRuta, Integer idDespacho) {
        return webCliente.get().uri(ub -> ub.path("/despacho/envios-disponibles/{id}")
                        .queryParamIfPresent("idDespacho", java.util.Optional.ofNullable(idDespacho))
                        .build(idRuta))
                .retrieve().bodyToFlux(EnvioResponseDto.class).collectList().block();
    }
}
