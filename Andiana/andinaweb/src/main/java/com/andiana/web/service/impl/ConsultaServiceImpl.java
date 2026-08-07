package com.andiana.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.andiana.web.model.dto.response.ConteoRecetaDto;
import com.andiana.web.model.dto.response.MateriaEnRecetaDto;
import com.andiana.web.model.dto.response.OpcionSelectDto;
import com.andiana.web.service.IConsultaService;
import com.andiana.web.service.IRecetaProduccionService;

@Service
public class ConsultaServiceImpl implements IConsultaService {

    private final WebClient webCliente;
    private final IRecetaProduccionService servicioReceta;

    public ConsultaServiceImpl(WebClient webCliente, IRecetaProduccionService servicioReceta) {
        this.webCliente = webCliente;
        this.servicioReceta = servicioReceta;
    }

    @Override
    public List<MateriaEnRecetaDto> materiasDeLaReceta(Integer idReceta) {
        return webCliente.get().uri(ub -> ub.path("/consulta/receta/{id}/materias").build(idReceta))
                .retrieve().bodyToFlux(MateriaEnRecetaDto.class).collectList().block();
    }

    @Override
    public List<ConteoRecetaDto> conteoDeMateriasPorReceta() {
        return webCliente.get().uri("/consulta/recetas/conteo-materias").retrieve()
                .bodyToFlux(ConteoRecetaDto.class).collectList().block();
    }

    /** El mismo texto que se ve en los formularios: producto y versión. */
    @Override
    public List<OpcionSelectDto> recetasParaElSelector() {
        return servicioReceta.listarOpciones();
    }
}
