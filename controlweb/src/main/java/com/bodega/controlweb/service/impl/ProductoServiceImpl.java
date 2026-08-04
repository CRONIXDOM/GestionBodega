package com.bodega.controlweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bodega.controlweb.model.dto.request.ProductoRequestDto;
import com.bodega.controlweb.model.dto.response.ProductoResponseDto;
import com.bodega.controlweb.service.IProductoService;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

@Service
public class ProductoServiceImpl implements IProductoService {

    private final WebClient webCliente;

    public ProductoServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<ProductoResponseDto> listarProducto() {
        return webCliente.get().uri("/producto").retrieve()
                .bodyToFlux(ProductoResponseDto.class).collectList().block();
    }

    @Override
    public void guardarProducto(ProductoRequestDto nuevo) {
        webCliente.post().uri("/producto").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public ProductoResponseDto buscarProductoId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/producto/buscarId/{id}").build(id))
                .retrieve().bodyToMono(ProductoResponseDto.class).block();
    }

    @Override
    public void eliminarProducto(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/producto/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public List<OpcionSelectDto> listarOpciones() {
        return listarProducto().stream()
                .map(op -> new OpcionSelectDto(op.getIdProducto(), op.getNombreProducto() + " (" + op.getCodigoProducto() + ")"))
                .toList();
    }
}
