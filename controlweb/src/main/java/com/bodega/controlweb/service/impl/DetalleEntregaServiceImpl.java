package com.bodega.controlweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bodega.controlweb.model.dto.request.DetalleEntregaRequestDto;
import com.bodega.controlweb.model.dto.response.DetalleEntregaResponseDto;
import com.bodega.controlweb.service.IDetalleEntregaService;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

@Service
public class DetalleEntregaServiceImpl implements IDetalleEntregaService {

    private final WebClient webCliente;

    public DetalleEntregaServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<DetalleEntregaResponseDto> listarDetalleEntrega() {
        return webCliente.get().uri("/detalleEntrega").retrieve()
                .bodyToFlux(DetalleEntregaResponseDto.class).collectList().block();
    }

    @Override
    public void guardarDetalleEntrega(DetalleEntregaRequestDto nuevo) {
        webCliente.post().uri("/detalleEntrega").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public DetalleEntregaResponseDto buscarDetalleEntregaId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/detalleEntrega/buscarId/{id}").build(id))
                .retrieve().bodyToMono(DetalleEntregaResponseDto.class).block();
    }

    @Override
    public void eliminarDetalleEntrega(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/detalleEntrega/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public List<OpcionSelectDto> listarOpciones() {
        return listarDetalleEntrega().stream()
                .map(op -> new OpcionSelectDto(op.getIdDetalleEntrega(), op.getNombreProducto() + " — evento " + op.getNombreEvento()))
                .toList();
    }
}
