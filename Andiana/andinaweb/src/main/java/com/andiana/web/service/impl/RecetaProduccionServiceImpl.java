package com.andiana.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.andiana.web.model.dto.request.RecetaProduccionRequestDto;
import com.andiana.web.model.dto.response.RecetaProduccionResponseDto;
import com.andiana.web.model.dto.response.OpcionSelectDto;
import com.andiana.web.model.dto.response.ProductoResponseDto;
import com.andiana.web.service.IProductoService;
import com.andiana.web.service.IRecetaProduccionService;

@Service
public class RecetaProduccionServiceImpl implements IRecetaProduccionService {

	private final WebClient webCliente;
	private final IProductoService servicioProducto;

	public RecetaProduccionServiceImpl(WebClient webCliente, IProductoService servicioProducto) {
		this.webCliente = webCliente;
		this.servicioProducto = servicioProducto;
	}

	@Override
	public List<RecetaProduccionResponseDto> listarReceta() {
		return webCliente.get().uri("/receta").retrieve().bodyToFlux(RecetaProduccionResponseDto.class).collectList()
				.block();
	}

	@Override
	public void guardarReceta(RecetaProduccionRequestDto nuevo) {
		webCliente.post().uri("/receta").bodyValue(nuevo).retrieve().toBodilessEntity().block();
	}

	@Override
	public RecetaProduccionResponseDto buscarRecetaId(Integer id) {
		return webCliente.get().uri(ub -> ub.path("/receta/buscarId/{id}").build(id)).retrieve()
				.bodyToMono(RecetaProduccionResponseDto.class).block();
	}

	@Override
	public void eliminarReceta(Integer id) {
		webCliente.delete().uri(ub -> ub.path("/receta/{id}").build(id)).retrieve().toBodilessEntity().block();
	}

	@Override
	public List<OpcionSelectDto> listarOpciones() {
		List<ProductoResponseDto> productos = servicioProducto.listarProducto();

		return listarReceta().stream().map(op -> new OpcionSelectDto(op.getIdReceta(),
				nombreDelProducto(productos, op.getIdProducto()) + " · versión " + op.getVersion())).toList();
	}

	private String nombreDelProducto(List<ProductoResponseDto> productos, Integer idProducto) {
		return productos.stream().filter(p -> p.getIdProducto().equals(idProducto))
				.map(p -> p.getNombre() + " " + p.getPresentacion()).findFirst().orElse("(producto eliminado)");
	}
}
