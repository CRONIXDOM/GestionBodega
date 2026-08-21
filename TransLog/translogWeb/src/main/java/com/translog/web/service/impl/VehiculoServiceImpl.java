package com.translog.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.translog.web.model.dto.request.VehiculoRequestDto;
import com.translog.web.model.dto.response.VehiculoResponseDto;
import com.translog.web.model.dto.response.OpcionSelectDto;
import com.translog.web.service.IVehiculoService;

@Service
public class VehiculoServiceImpl implements IVehiculoService {

    private final WebClient webCliente;

    public VehiculoServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<VehiculoResponseDto> listarVehiculo() {
        return webCliente.get().uri("/vehiculo").retrieve()
                .bodyToFlux(VehiculoResponseDto.class).collectList().block();
    }

    @Override
    public void guardarVehiculo(VehiculoRequestDto nuevo) {
        webCliente.post().uri("/vehiculo").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public VehiculoResponseDto buscarVehiculoId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/vehiculo/buscarId/{id}").build(id))
                .retrieve().bodyToMono(VehiculoResponseDto.class).block();
    }

    @Override
    public void eliminarVehiculo(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/vehiculo/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public List<OpcionSelectDto> listarOpciones() {
        return listarVehiculo().stream()
                .map(op -> new OpcionSelectDto(op.getIdVehiculo(), op.getPlaca() + " (" + op.getCapacidadMaxima() + " kg)"))
                .toList();
    }
}
