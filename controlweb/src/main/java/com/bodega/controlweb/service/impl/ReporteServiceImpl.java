package com.bodega.controlweb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bodega.controlweb.model.dto.request.ReporteRequestDto;
import com.bodega.controlweb.model.dto.response.ReporteResponseDto;
import com.bodega.controlweb.service.IReporteService;

@Service
public class ReporteServiceImpl implements IReporteService {

    private final WebClient webCliente;

    public ReporteServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<ReporteResponseDto> listarReporte() {
        return webCliente.get().uri("/reporte").retrieve()
                .bodyToFlux(ReporteResponseDto.class).collectList().block();
    }

    @Override
    public void guardarReporte(ReporteRequestDto nuevo) {
        webCliente.post().uri("/reporte").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public ReporteResponseDto buscarReporteId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/reporte/buscarId/{id}").build(id))
                .retrieve().bodyToMono(ReporteResponseDto.class).block();
    }

    @Override
    public void eliminarReporte(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/reporte/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }
}
