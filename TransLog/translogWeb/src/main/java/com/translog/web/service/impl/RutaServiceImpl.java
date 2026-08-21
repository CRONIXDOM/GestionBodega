package com.translog.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.translog.web.model.dto.request.RutaRequestDto;
import com.translog.web.model.dto.response.RutaResponseDto;
import com.translog.web.model.dto.response.OpcionSelectDto;
import com.translog.web.service.ICiudadService;
import com.translog.web.service.IRutaService;

@Service
public class RutaServiceImpl implements IRutaService {

    private final WebClient webCliente;
    private final ICiudadService servicioCiudad;

    public RutaServiceImpl(WebClient webCliente, ICiudadService servicioCiudad) {
        this.webCliente = webCliente;
        this.servicioCiudad = servicioCiudad;
    }

    @Override
    public List<RutaResponseDto> listarRuta() {
        return webCliente.get().uri("/ruta").retrieve()
                .bodyToFlux(RutaResponseDto.class).collectList().block();
    }

    @Override
    public void guardarRuta(RutaRequestDto nuevo) {
        webCliente.post().uri("/ruta").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public RutaResponseDto buscarRutaId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/ruta/buscarId/{id}").build(id))
                .retrieve().bodyToMono(RutaResponseDto.class).block();
    }

    @Override
    public void eliminarRuta(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/ruta/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    /** La ruta se nombra por sus ciudades, que es como la reconoce el usuario. */
    @Override
    public List<OpcionSelectDto> listarOpciones() {
        java.util.Map<Integer, String> nombres = new java.util.HashMap<>();
        servicioCiudad.listarCiudad().forEach(c -> nombres.put(c.getIdCiudad(), c.getNombre()));

        return listarRuta().stream()
                .map(op -> new OpcionSelectDto(op.getIdRuta(),
                        nombres.getOrDefault(op.getIdCiudadOrigen(), "?") + " → "
                                + nombres.getOrDefault(op.getIdCiudadDestino(), "?")))
                .toList();
    }
}
