package com.bodega.controlweb.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bodega.controlweb.model.dto.response.MovimientoReporteResponseDto;
import com.bodega.controlweb.service.IReporteService;

@Service
public class ReporteServiceImpl implements IReporteService {

    private final WebClient webCliente;

    public ReporteServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<MovimientoReporteResponseDto> buscarMovimientos(LocalDate desde, LocalDate hasta, Integer idTipo,
            Integer idSede) {
        return webCliente.get()
                .uri(ub -> {
                    ub.path("/reporte/movimientos");
                    if (desde != null) {
                        ub.queryParam("desde", desde);
                    }
                    if (hasta != null) {
                        ub.queryParam("hasta", hasta);
                    }
                    if (idTipo != null) {
                        ub.queryParam("idTipo", idTipo);
                    }
                    if (idSede != null) {
                        ub.queryParam("idSede", idSede);
                    }
                    return ub.build();
                })
                .retrieve().bodyToFlux(MovimientoReporteResponseDto.class).collectList().block();
    }
}
