package com.translog.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.translog.web.model.dto.response.DespachoConEnviosDto;
import com.translog.web.service.IConsultaService;

@Service
public class ConsultaServiceImpl implements IConsultaService {

    private final WebClient webCliente;

    public ConsultaServiceImpl(WebClient webCliente) {
        this.webCliente = webCliente;
    }

    @Override
    public List<DespachoConEnviosDto> despachosConMasEnvios() {
        return webCliente.get().uri("/consulta/despachos-con-mas-envios").retrieve()
                .bodyToFlux(DespachoConEnviosDto.class).collectList().block();
    }
}
