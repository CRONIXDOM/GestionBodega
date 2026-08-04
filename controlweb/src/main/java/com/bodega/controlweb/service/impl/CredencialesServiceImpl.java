package com.bodega.controlweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bodega.controlweb.model.dto.request.CredencialesRequestDto;
import com.bodega.controlweb.model.dto.response.CredencialesResponseDto;
import com.bodega.controlweb.service.ICredencialesService;

@Service
public class CredencialesServiceImpl implements ICredencialesService {

    private final WebClient webCliente;

    public CredencialesServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<CredencialesResponseDto> listarCredenciales() {
        return webCliente.get().uri("/credenciales").retrieve()
                .bodyToFlux(CredencialesResponseDto.class).collectList().block();
    }

    @Override
    public void guardarCredenciales(CredencialesRequestDto nuevo) {
        webCliente.post().uri("/credenciales").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public CredencialesResponseDto buscarCredencialesId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/credenciales/buscarId/{id}").build(id))
                .retrieve().bodyToMono(CredencialesResponseDto.class).block();
    }

    @Override
    public void eliminarCredenciales(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/credenciales/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public List<CredencialesResponseDto> buscarPorUsuario(String usuario) {
        return webCliente.get().uri(ub -> ub.path("/credenciales/usuario/{usuario}").build(usuario))
                .retrieve().bodyToFlux(CredencialesResponseDto.class).collectList().block();
    }
}
