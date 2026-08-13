package com.andiana.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.andiana.web.model.dto.request.LoteProduccionRequestDto;
import com.andiana.web.model.dto.response.LoteProduccionResponseDto;
import com.andiana.web.model.dto.response.OpcionSelectDto;
import com.andiana.web.service.ILoteProduccionService;

@Service
public class LoteProduccionServiceImpl implements ILoteProduccionService {

	private final WebClient webCliente;

	public LoteProduccionServiceImpl(WebClient webCliente) {
		this.webCliente = webCliente;
	}

	@Override
	public List<LoteProduccionResponseDto> listarLote() {
		return webCliente.get().uri("/lote").retrieve().bodyToFlux(LoteProduccionResponseDto.class).collectList()
				.block();
	}

	@Override
	public void guardarLote(LoteProduccionRequestDto nuevo) {
		webCliente.post().uri("/lote").bodyValue(nuevo).retrieve().toBodilessEntity().block();
	}

	@Override
	public LoteProduccionResponseDto buscarLoteId(Integer id) {
		return webCliente.get().uri(ub -> ub.path("/lote/buscarId/{id}").build(id)).retrieve()
				.bodyToMono(LoteProduccionResponseDto.class).block();
	}

	@Override
	public void eliminarLote(Integer id) {
		webCliente.delete().uri(ub -> ub.path("/lote/{id}").build(id)).retrieve().toBodilessEntity().block();
	}

	@Override
	public List<OpcionSelectDto> listarOpciones() {
		return listarLote().stream().map(op -> new OpcionSelectDto(op.getIdLote(), op.getNumeroLote())).toList();
	}
}
