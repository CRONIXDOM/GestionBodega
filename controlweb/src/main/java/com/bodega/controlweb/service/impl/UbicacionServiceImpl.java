package com.bodega.controlweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bodega.controlweb.model.dto.request.UbicacionRequestDto;
import com.bodega.controlweb.model.dto.response.UbicacionResponseDto;
import com.bodega.controlweb.service.IUbicacionService;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

@Service
public class UbicacionServiceImpl implements IUbicacionService {

    private final WebClient webCliente;

    public UbicacionServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<UbicacionResponseDto> listarUbicacion() {
        return webCliente.get().uri("/ubicacion").retrieve()
                .bodyToFlux(UbicacionResponseDto.class).collectList().block();
    }

    @Override
    public void guardarUbicacion(UbicacionRequestDto nuevo) {
        webCliente.post().uri("/ubicacion").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public UbicacionResponseDto buscarUbicacionId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/ubicacion/buscarId/{id}").build(id))
                .retrieve().bodyToMono(UbicacionResponseDto.class).block();
    }

    @Override
    public void eliminarUbicacion(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/ubicacion/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public List<OpcionSelectDto> listarOpciones() {
        return listarUbicacion().stream()
                .map(op -> new OpcionSelectDto(op.getIdUbicacion(), op.getCodigoUbicacion() + " (Zona " + op.getIdZona() + ")"))
                .toList();
    }
}
