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

import com.bodega.control.aplicacion.casosuso.entrada.ISolicitudUseCase;
import com.bodega.control.presentacion.dto.request.SolicitudRequestDto;
import com.bodega.control.presentacion.dto.response.SolicitudResponseDto;
import com.bodega.control.presentacion.mapeadores.ISolicitudDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/solicitud")
public class SolicitudController {

	private final ISolicitudUseCase solicitudUseCase;
	private final ISolicitudDtoMapper mapper;

	public SolicitudController(ISolicitudUseCase solicitudUseCase, ISolicitudDtoMapper mapper) {

		this.solicitudUseCase = solicitudUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public SolicitudResponseDto guardar(@Valid @RequestBody SolicitudRequestDto request) {

		return mapper.toResponseDto(solicitudUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<SolicitudResponseDto> listarTodo() {

		return solicitudUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idSolicitud}")
	public ResponseEntity<Void> eliminar(@PathVariable int idSolicitud) {

		solicitudUseCase.eliminar(idSolicitud);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idSolicitud}")
	public SolicitudResponseDto buscarPorId(@PathVariable int idSolicitud) {

		return mapper.toResponseDto(solicitudUseCase.buscarPorId(idSolicitud));
	}

}