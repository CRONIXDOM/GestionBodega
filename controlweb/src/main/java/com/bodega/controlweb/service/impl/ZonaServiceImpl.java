package com.bodega.controlweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bodega.controlweb.model.dto.request.ZonaRequestDto;
import com.bodega.controlweb.model.dto.response.ZonaResponseDto;
import com.bodega.controlweb.service.IZonaService;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

@Service
public class ZonaServiceImpl implements IZonaService {

    private final WebClient webCliente;

    public ZonaServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<ZonaResponseDto> listarZona() {
        return webCliente.get().uri("/zona").retrieve()
                .bodyToFlux(ZonaResponseDto.class).collectList().block();
    }

    @Override
    public ZonaResponseDto guardarZona(ZonaRequestDto nuevo) {
        return webCliente.post().uri("/zona").bodyValue(nuevo).retrieve()
                .bodyToMono(ZonaResponseDto.class).block();
    }

    @Override
    public ZonaResponseDto buscarZonaId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/zona/buscarId/{id}").build(id))
                .retrieve().bodyToMono(ZonaResponseDto.class).block();
    }

    @Override
    public void eliminarZona(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/zona/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public List<OpcionSelectDto> listarOpciones() {
        return listarZona().stream()
                .map(op -> new OpcionSelectDto(op.getIdZona(), op.getNombreZona()))
                .toList();
    }
}
