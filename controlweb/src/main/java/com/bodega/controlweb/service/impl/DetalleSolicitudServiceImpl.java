package com.bodega.controlweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bodega.controlweb.model.dto.request.DetalleSolicitudRequestDto;
import com.bodega.controlweb.model.dto.response.DetalleSolicitudResponseDto;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;
import com.bodega.controlweb.service.IDetalleSolicitudService;

@Service
public class DetalleSolicitudServiceImpl implements IDetalleSolicitudService {

    private final WebClient webCliente;

    public DetalleSolicitudServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<DetalleSolicitudResponseDto> listarDetalleSolicitud() {
        return webCliente.get().uri("/detalleSolicitud").retrieve()
                .bodyToFlux(DetalleSolicitudResponseDto.class).collectList().block();
    }

    @Override
    public void guardarDetalleSolicitud(DetalleSolicitudRequestDto nuevo) {
        webCliente.post().uri("/detalleSolicitud").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public DetalleSolicitudResponseDto buscarDetalleSolicitudId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/detalleSolicitud/buscarId/{id}").build(id))
                .retrieve().bodyToMono(DetalleSolicitudResponseDto.class).block();
    }

    @Override
    public void eliminarDetalleSolicitud(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/detalleSolicitud/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public List<OpcionSelectDto> listarOpciones() {
        return listarDetalleSolicitud().stream()
                .map(op -> new OpcionSelectDto(op.getIdDetalleSolicitud(),
                        "Pedido #" + op.getIdDetalleSolicitud() + " — " + op.getCantidadProducto()
                                + " uds (Solicitud #" + op.getIdSolicitud() + ")"))
                .toList();
    }
}
