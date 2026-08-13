package com.andiana.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.andiana.web.model.dto.request.MovimientoMateriaPrimaRequestDto;
import com.andiana.web.model.dto.response.MovimientoMateriaPrimaResponseDto;
import com.andiana.web.model.dto.response.OpcionSelectDto;
import com.andiana.web.service.IMovimientoMateriaPrimaService;

@Service
public class MovimientoMateriaPrimaServiceImpl implements IMovimientoMateriaPrimaService {

	private final WebClient webCliente;

	public MovimientoMateriaPrimaServiceImpl(WebClient webCliente) {
		this.webCliente = webCliente;
	}

	@Override
	public List<MovimientoMateriaPrimaResponseDto> listarMovimiento() {
		return webCliente.get().uri("/movimiento").retrieve().bodyToFlux(MovimientoMateriaPrimaResponseDto.class)
				.collectList().block();
	}

	@Override
	public void guardarMovimiento(MovimientoMateriaPrimaRequestDto nuevo) {
		webCliente.post().uri("/movimiento").bodyValue(nuevo).retrieve().toBodilessEntity().block();
	}

	@Override
	public MovimientoMateriaPrimaResponseDto buscarMovimientoId(Integer id) {
		return webCliente.get().uri(ub -> ub.path("/movimiento/buscarId/{id}").build(id)).retrieve()
				.bodyToMono(MovimientoMateriaPrimaResponseDto.class).block();
	}

	@Override
	public void eliminarMovimiento(Integer id) {
		webCliente.delete().uri(ub -> ub.path("/movimiento/{id}").build(id)).retrieve().toBodilessEntity().block();
	}

	@Override
	public List<OpcionSelectDto> listarOpciones() {
		return listarMovimiento().stream()
				.map(op -> new OpcionSelectDto(op.getIdMovimiento(), "Movimiento " + op.getIdMovimiento())).toList();
	}
}
