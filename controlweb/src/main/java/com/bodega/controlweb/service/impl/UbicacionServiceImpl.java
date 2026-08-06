package com.bodega.controlweb.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bodega.controlweb.model.dto.request.UbicacionRequestDto;
import com.bodega.controlweb.model.dto.response.SedeResponseDto;
import com.bodega.controlweb.model.dto.response.UbicacionResponseDto;
import com.bodega.controlweb.service.ISedeService;
import com.bodega.controlweb.service.IUbicacionService;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

@Service
public class UbicacionServiceImpl implements IUbicacionService {

    private final WebClient webCliente;
    private final ISedeService servicioSede;

    public UbicacionServiceImpl(WebClient webCliente, ISedeService servicioSede) {
        this.webCliente = webCliente;
        this.servicioSede = servicioSede;
    }

    @Override
    public List<UbicacionResponseDto> listarUbicacion() {
        return webCliente.get().uri("/ubicacion").retrieve()
                .bodyToFlux(UbicacionResponseDto.class).collectList().block();
    }

    @Override
    public void guardarUbicacion(UbicacionRequestDto nuevo) {
        webCliente.post().uri("/ubicacion").bodyValue(nuevo).retrieve().toBodilessEntity().block();
    }

    @Override
    public UbicacionResponseDto buscarUbicacionId(Integer id) {
        return webCliente.get().uri(ub -> ub.path("/ubicacion/buscarId/{id}").build(id))
                .retrieve().bodyToMono(UbicacionResponseDto.class).block();
    }

    @Override
    public void eliminarUbicacion(Integer id) {
        webCliente.delete().uri(ub -> ub.path("/ubicacion/{id}").build(id))
                .retrieve().toBodilessEntity().block();
    }

    /**
     * Las ubicaciones ya no se manejan a mano: cada bodega tiene la suya y se
     * crea sola. Por eso en los desplegables se muestra el nombre de la bodega y
     * no un código interno que al usuario no le dice nada.
     */
    @Override
    public List<OpcionSelectDto> listarOpciones() {
        Map<Integer, String> nombrePorSede = new HashMap<>();
        for (SedeResponseDto sede : servicioSede.listarSede()) {
            nombrePorSede.put(sede.getIdSede(), sede.getNombreSede());
        }

        // una bodega registrada antes de unificar la pantalla puede arrastrar varias
        // ubicaciones; en el desplegable debe salir una sola vez.
        List<OpcionSelectDto> opciones = new ArrayList<>();
        Set<Integer> sedesYaPuestas = new HashSet<>();
        for (UbicacionResponseDto u : listarUbicacion()) {
            if (u.getIdSede() != null && !sedesYaPuestas.add(u.getIdSede())) {
                continue;
            }
            opciones.add(new OpcionSelectDto(u.getIdUbicacion(),
                    nombrePorSede.getOrDefault(u.getIdSede(), u.getCodigoUbicacion())));
        }
        return opciones;
    }
}
