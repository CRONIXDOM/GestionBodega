package com.translog.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.translog.web.model.dto.request.CiudadRequestDto;
import com.translog.web.model.dto.response.CiudadResponseDto;
import com.translog.web.model.dto.response.OpcionSelectDto;
import com.translog.web.service.ICiudadService;

@Service
public class CiudadServiceImpl implements ICiudadService {

    private final WebClient webCliente;

    public CiudadServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<CiudadResponseDto> listarCiudad() {
        return webCliente.get().uri("/ciudad").retrieve()
                .bodyToFlux(CiudadResponseDto.class).collectList().block();
    }

    @Override
    public void guardarCiudad(CiudadRequestDto nuevo) {
        webCliente.post().uri("/ciudad").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public CiudadResponseDto buscarCiudadId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/ciudad/buscarId/{id}").build(id))
                .retrieve().bodyToMono(CiudadResponseDto.class).block();
    }

    @Override
    public void eliminarCiudad(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/ciudad/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public List<OpcionSelectDto> listarOpciones() {
        return listarCiudad().stream()
                .map(op -> new OpcionSelectDto(op.getIdCiudad(), op.getNombre()))
                .toList();
    }
}
