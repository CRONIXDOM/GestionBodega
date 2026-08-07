package com.andiana.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.andiana.web.model.dto.request.InventarioProductoRequestDto;
import com.andiana.web.model.dto.response.InventarioProductoResponseDto;
import com.andiana.web.model.dto.response.OpcionSelectDto;
import com.andiana.web.service.IInventarioProductoService;

@Service
public class InventarioProductoServiceImpl implements IInventarioProductoService {

    private final WebClient webCliente;

    public InventarioProductoServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<InventarioProductoResponseDto> listarInventario() {
        return webCliente.get().uri("/inventario").retrieve()
                .bodyToFlux(InventarioProductoResponseDto.class).collectList().block();
    }

    @Override
    public void guardarInventario(InventarioProductoRequestDto nuevo) {
        webCliente.post().uri("/inventario").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public InventarioProductoResponseDto buscarInventarioId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/inventario/buscarId/{id}").build(id))
                .retrieve().bodyToMono(InventarioProductoResponseDto.class).block();
    }

    @Override
    public void eliminarInventario(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/inventario/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public List<OpcionSelectDto> listarOpciones() {
        return listarInventario().stream()
                .map(op -> new OpcionSelectDto(op.getIdInventario(), "Registro " + op.getIdInventario()))
                .toList();
    }
}
