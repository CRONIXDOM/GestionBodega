package com.andiana.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.andiana.web.model.dto.request.ProductoRequestDto;
import com.andiana.web.model.dto.response.ProductoResponseDto;
import com.andiana.web.model.dto.response.OpcionSelectDto;
import com.andiana.web.service.IProductoService;

@Service
public class ProductoServiceImpl implements IProductoService {

	private final WebClient webCliente;

	public ProductoServiceImpl(WebClient webCliente) {
		this.webCliente = webCliente;
	}

	@Override
	public List<ProductoResponseDto> listarProducto() {
		return webCliente.get().uri("/producto").retrieve().bodyToFlux(ProductoResponseDto.class).collectList().block();
	}

	@Override
	public void guardarProducto(ProductoRequestDto nuevo) {
		webCliente.post().uri("/producto").bodyValue(nuevo).retrieve().toBodilessEntity().block();
	}

	@Override
	public ProductoResponseDto buscarProductoId(Integer id) {
		return webCliente.get().uri(ub -> ub.path("/producto/buscarId/{id}").build(id)).retrieve()
				.bodyToMono(ProductoResponseDto.class).block();
	}

	@Override
	public void eliminarProducto(Integer id) {
		webCliente.delete().uri(ub -> ub.path("/producto/{id}").build(id)).retrieve().toBodilessEntity().block();
	}

	@Override
	public List<OpcionSelectDto> listarOpciones() {
		return listarProducto().stream().map(op -> new OpcionSelectDto(op.getIdProducto(),
				op.getNombre() + " " + op.getPresentacion() + " (" + op.getVolumenMl() + " ml)")).toList();
	}
}
