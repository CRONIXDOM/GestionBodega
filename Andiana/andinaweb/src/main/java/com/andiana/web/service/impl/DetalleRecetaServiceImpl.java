package com.andiana.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.andiana.web.model.dto.request.DetalleRecetaRequestDto;
import com.andiana.web.model.dto.response.DetalleRecetaResponseDto;
import com.andiana.web.model.dto.response.OpcionSelectDto;
import com.andiana.web.service.IDetalleRecetaService;

@Service
public class DetalleRecetaServiceImpl implements IDetalleRecetaService {

	private final WebClient webCliente;

	public DetalleRecetaServiceImpl(WebClient webCliente) {
		this.webCliente = webCliente;
	}

	@Override
	public List<DetalleRecetaResponseDto> listarDetalleReceta() {
		return webCliente.get().uri("/detalleReceta").retrieve().bodyToFlux(DetalleRecetaResponseDto.class)
				.collectList().block();
	}

	@Override
	public void guardarDetalleReceta(DetalleRecetaRequestDto nuevo) {
		webCliente.post().uri("/detalleReceta").bodyValue(nuevo).retrieve().toBodilessEntity().block();
	}

	@Override
	public void guardarVariasDetalleReceta(List<DetalleRecetaRequestDto> lineas) {
		webCliente.post().uri("/detalleReceta/varias").bodyValue(lineas).retrieve().toBodilessEntity().block();
	}

	@Override
	public DetalleRecetaResponseDto buscarDetalleRecetaId(Integer id) {
		return webCliente.get().uri(ub -> ub.path("/detalleReceta/buscarId/{id}").build(id)).retrieve()
				.bodyToMono(DetalleRecetaResponseDto.class).block();
	}

	@Override
	public void eliminarDetalleReceta(Integer id) {
		webCliente.delete().uri(ub -> ub.path("/detalleReceta/{id}").build(id)).retrieve().toBodilessEntity().block();
	}

	@Override
	public List<OpcionSelectDto> listarOpciones() {
		return listarDetalleReceta().stream()
				.map(op -> new OpcionSelectDto(op.getIdDetalle(), "Línea " + op.getIdDetalle())).toList();
	}
}
