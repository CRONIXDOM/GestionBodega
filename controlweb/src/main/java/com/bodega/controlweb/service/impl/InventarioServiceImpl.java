package com.bodega.controlweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bodega.controlweb.model.dto.response.InventarioResponseDto;
import com.bodega.controlweb.service.IInventarioService;

@Service
public class InventarioServiceImpl implements IInventarioService {

    private final WebClient webCliente;

    public InventarioServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<InventarioResponseDto> listarInventario() {
        return webCliente.get().uri("/inventario").retrieve()
                .bodyToFlux(InventarioResponseDto.class).collectList().block();
    }
}
