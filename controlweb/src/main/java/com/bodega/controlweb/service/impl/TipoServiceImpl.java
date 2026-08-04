package com.bodega.controlweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bodega.controlweb.model.dto.request.TipoRequestDto;
import com.bodega.controlweb.model.dto.response.TipoResponseDto;
import com.bodega.controlweb.service.ITipoService;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

@Service
public class TipoServiceImpl implements ITipoService {

    private final WebClient webCliente;

    public TipoServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<TipoResponseDto> listarTipo() {
        return webCliente.get().uri("/tipo").retrieve()
                .bodyToFlux(TipoResponseDto.class).collectList().block();
    }

    @Override
    public void guardarTipo(TipoRequestDto nuevo) {
        webCliente.post().uri("/tipo").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public TipoResponseDto buscarTipoId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/tipo/buscarId/{id}").build(id))
                .retrieve().bodyToMono(TipoResponseDto.class).block();
    }

    @Override
    public void eliminarTipo(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/tipo/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public List<OpcionSelectDto> listarOpciones() {
        return listarTipo().stream()
                .map(op -> new OpcionSelectDto(op.getIdTipo(), op.getDescripcion() + " (" + op.getClase() + ")"))
                .toList();
    }
}
