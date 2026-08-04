package com.bodega.control.presentacion.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bodega.control.aplicacion.casosuso.entrada.IEntregaUseCase;
import com.bodega.control.presentacion.dto.request.EntregaRequestDto;
import com.bodega.control.presentacion.dto.response.EntregaResponseDto;
import com.bodega.control.presentacion.mapeadores.IEntregaDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/entrega")
public class EntregaController {

	private final IEntregaUseCase entregaUseCase;
	private final IEntregaDtoMapper mapper;

	public EntregaController(IEntregaUseCase entregaUseCase, IEntregaDtoMapper mapper) {
		this.entregaUseCase = entregaUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EntregaResponseDto guardar(@Valid @RequestBody EntregaRequestDto request) {

		return mapper.toResponseDto(entregaUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<EntregaResponseDto> listarTodo() {

		return entregaUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idEntrega}")
	public ResponseEntity<Void> eliminar(@PathVariable int idEntrega) {

	    entregaUseCase.eliminar(idEntrega);

	    return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idEntrega}")
	public EntregaResponseDto buscarPorId(@PathVariable int idEntrega) {

		return mapper.toResponseDto(entregaUseCase.buscarPorId(idEntrega));
	}

}