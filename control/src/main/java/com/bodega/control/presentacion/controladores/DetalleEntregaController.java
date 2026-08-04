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
import com.bodega.control.aplicacion.casosuso.entrada.IDetalleEntregaUseCase;
import com.bodega.control.presentacion.dto.request.DetalleEntregaRequestDto;
import com.bodega.control.presentacion.dto.response.DetalleEntregaResponseDto;
import com.bodega.control.presentacion.mapeadores.IDetalleEntregaDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/detalleEntrega")
public class DetalleEntregaController {

	private final IDetalleEntregaUseCase detalleEntregaUseCase;
	private final IDetalleEntregaDtoMapper mapper;

	public DetalleEntregaController(IDetalleEntregaUseCase detalleEntregaUseCase, IDetalleEntregaDtoMapper mapper) {

		this.detalleEntregaUseCase = detalleEntregaUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DetalleEntregaResponseDto guardar(@Valid @RequestBody DetalleEntregaRequestDto request) {

		return mapper.toResponseDto(detalleEntregaUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<DetalleEntregaResponseDto> listarTodo() {

		return detalleEntregaUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idDetalleEntrega}")
	public ResponseEntity<Void> eliminar(@PathVariable int idDetalleEntrega) {

		detalleEntregaUseCase.eliminar(idDetalleEntrega);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idDetalleEntrega}")
	public DetalleEntregaResponseDto buscarPorId(@PathVariable int idDetalleEntrega) {

		return mapper.toResponseDto(detalleEntregaUseCase.buscarPorid(idDetalleEntrega));
	}

}