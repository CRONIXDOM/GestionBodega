package com.bodega.controlweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bodega.controlweb.model.dto.request.RegistroRequestDto;
import com.bodega.controlweb.model.dto.response.RegistroResponseDto;
import com.bodega.controlweb.service.IRegistroService;

@Service
public class RegistroServiceImpl implements IRegistroService {

    private final WebClient webCliente;

    public RegistroServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<RegistroResponseDto> listarRegistro() {
        return webCliente.get().uri("/registro").retrieve()
                .bodyToFlux(RegistroResponseDto.class).collectList().block();
    }

    @Override
    public void guardarRegistro(RegistroRequestDto nuevo) {
        webCliente.post().uri("/registro").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public RegistroResponseDto buscarRegistroId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/registro/buscarId/{id}").build(id))
                .retrieve().bodyToMono(RegistroResponseDto.class).block();
    }

    @Override
    public void eliminarRegistro(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/registro/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }
}
