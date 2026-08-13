package com.andiana.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.andiana.web.model.dto.request.ControlCalidadRequestDto;
import com.andiana.web.model.dto.response.ControlCalidadResponseDto;
import com.andiana.web.model.dto.response.OpcionSelectDto;
import com.andiana.web.service.IControlCalidadService;

@Service
public class ControlCalidadServiceImpl implements IControlCalidadService {

	private final WebClient webCliente;

	public ControlCalidadServiceImpl(WebClient webCliente) {
		this.webCliente = webCliente;
	}

	@Override
	public List<ControlCalidadResponseDto> listarControlCalidad() {
		return webCliente.get().uri("/controlCalidad").retrieve().bodyToFlux(ControlCalidadResponseDto.class)
				.collectList().block();
	}

	@Override
	public void guardarControlCalidad(ControlCalidadRequestDto nuevo) {
		webCliente.post().uri("/controlCalidad").bodyValue(nuevo).retrieve().toBodilessEntity().block();
	}

	@Override
	public ControlCalidadResponseDto buscarControlCalidadId(Integer id) {
		return webCliente.get().uri(ub -> ub.path("/controlCalidad/buscarId/{id}").build(id)).retrieve()
				.bodyToMono(ControlCalidadResponseDto.class).block();
	}

	@Override
	public void eliminarControlCalidad(Integer id) {
		webCliente.delete().uri(ub -> ub.path("/controlCalidad/{id}").build(id)).retrieve().toBodilessEntity().block();
	}

	@Override
	public List<OpcionSelectDto> listarOpciones() {
		return listarControlCalidad().stream()
				.map(op -> new OpcionSelectDto(op.getIdControl(), "Control " + op.getIdControl())).toList();
	}
}
