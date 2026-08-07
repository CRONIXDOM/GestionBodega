package com.andiana.api.presentacion.controladores;

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

import com.andiana.api.aplicacion.casosuso.entrada.IDetalleRecetaUseCase;
import com.andiana.api.presentacion.dto.request.DetalleRecetaRequestDto;
import com.andiana.api.presentacion.dto.response.DetalleRecetaResponseDto;
import com.andiana.api.presentacion.mapeadores.IDetalleRecetaDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/detalleReceta")
public class DetalleRecetaController {

	private final IDetalleRecetaUseCase detalleRecetaUseCase;
	private final IDetalleRecetaDtoMapper mapper;

	public DetalleRecetaController(IDetalleRecetaUseCase detalleRecetaUseCase, IDetalleRecetaDtoMapper mapper) {

		this.detalleRecetaUseCase = detalleRecetaUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DetalleRecetaResponseDto guardar(@Valid @RequestBody DetalleRecetaRequestDto request) {

		return mapper.toResponseDto(detalleRecetaUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<DetalleRecetaResponseDto> listarTodo() {

		return detalleRecetaUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idDetalle}")
	public ResponseEntity<Void> eliminar(@PathVariable int idDetalle) {

		detalleRecetaUseCase.eliminar(idDetalle);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idDetalle}")
	public DetalleRecetaResponseDto buscarPorId(@PathVariable int idDetalle) {

		return mapper.toResponseDto(detalleRecetaUseCase.buscarPorId(idDetalle));
	}

}
