package com.bodega.controlweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bodega.controlweb.model.dto.request.SolicitudRequestDto;
import com.bodega.controlweb.model.dto.response.SolicitudResponseDto;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;
import com.bodega.controlweb.service.ISolicitudService;

@Service
public class SolicitudServiceImpl implements ISolicitudService {

    private final WebClient webCliente;

    public SolicitudServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<SolicitudResponseDto> listarSolicitud() {
        return webCliente.get().uri("/solicitud").retrieve()
                .bodyToFlux(SolicitudResponseDto.class).collectList().block();
    }

    @Override
    public void guardarSolicitud(SolicitudRequestDto nuevo) {
        webCliente.post().uri("/solicitud").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public SolicitudResponseDto buscarSolicitudId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/solicitud/buscarId/{id}").build(id))
                .retrieve().bodyToMono(SolicitudResponseDto.class).block();
    }

    @Override
    public void eliminarSolicitud(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/solicitud/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public List<OpcionSelectDto> listarOpciones() {
        return listarSolicitud().stream()
                .map(s -> new OpcionSelectDto(s.getIdSolicitud(),
                        "Solicitud #" + s.getIdSolicitud() + " — " + s.getFechaSolicitud()))
                .toList();
    }
}
