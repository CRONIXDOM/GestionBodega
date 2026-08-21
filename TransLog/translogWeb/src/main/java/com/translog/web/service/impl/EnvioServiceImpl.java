package com.translog.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.translog.web.model.dto.request.EnvioRequestDto;
import com.translog.web.model.dto.response.EnvioResponseDto;
import com.translog.web.model.dto.response.OpcionSelectDto;
import com.translog.web.service.IEnvioService;

@Service
public class EnvioServiceImpl implements IEnvioService {

    private final WebClient webCliente;

    public EnvioServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<EnvioResponseDto> listarEnvio() {
        return webCliente.get().uri("/envio").retrieve()
                .bodyToFlux(EnvioResponseDto.class).collectList().block();
    }

    @Override
    public void guardarEnvio(EnvioRequestDto nuevo) {
        webCliente.post().uri("/envio").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public EnvioResponseDto buscarEnvioId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/envio/buscarId/{id}").build(id))
                .retrieve().bodyToMono(EnvioResponseDto.class).block();
    }

    @Override
    public void eliminarEnvio(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/envio/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public List<OpcionSelectDto> listarOpciones() {
        return listarEnvio().stream()
                .map(op -> new OpcionSelectDto(op.getIdEnvio(), "Envío " + op.getIdEnvio()))
                .toList();
    }
}
