package com.andiana.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.andiana.web.model.dto.request.MateriaPrimaRequestDto;
import com.andiana.web.model.dto.response.MateriaPrimaResponseDto;
import com.andiana.web.model.dto.response.OpcionSelectDto;
import com.andiana.web.service.IMateriaPrimaService;

@Service
public class MateriaPrimaServiceImpl implements IMateriaPrimaService {

    private final WebClient webCliente;

    public MateriaPrimaServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<MateriaPrimaResponseDto> listarMateriaPrima() {
        return webCliente.get().uri("/materiaPrima").retrieve()
                .bodyToFlux(MateriaPrimaResponseDto.class).collectList().block();
    }

    @Override
    public void guardarMateriaPrima(MateriaPrimaRequestDto nuevo) {
        webCliente.post().uri("/materiaPrima").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public MateriaPrimaResponseDto buscarMateriaPrimaId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/materiaPrima/buscarId/{id}").build(id))
                .retrieve().bodyToMono(MateriaPrimaResponseDto.class).block();
    }

    @Override
    public void eliminarMateriaPrima(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/materiaPrima/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public List<OpcionSelectDto> listarOpciones() {
        return listarMateriaPrima().stream()
                .map(op -> new OpcionSelectDto(op.getIdMateria(), op.getNombre() + " (" + op.getUnidadMedida() + ")"))
                .toList();
    }
}
