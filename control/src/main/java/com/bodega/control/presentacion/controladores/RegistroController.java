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
import com.bodega.control.aplicacion.casosuso.entrada.IRegistroUseCase;
import com.bodega.control.presentacion.dto.request.RegistroRequestDto;
import com.bodega.control.presentacion.dto.response.RegistroResponseDto;
import com.bodega.control.presentacion.mapeadores.IRegistroDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/registro")
public class RegistroController {

	private final IRegistroUseCase registroUseCase;
	private final IRegistroDtoMapper mapper;

	public RegistroController(IRegistroUseCase registroUseCase, IRegistroDtoMapper mapper) {

		this.registroUseCase = registroUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RegistroResponseDto guardar(@Valid @RequestBody RegistroRequestDto request) {

		return mapper.toResponseDto(registroUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<RegistroResponseDto> listarTodo() {

		return registroUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idRegistro}")
	public ResponseEntity<Void> eliminar(@PathVariable int idRegistro) {

		registroUseCase.eliminar(idRegistro);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idRegistro}")
	public RegistroResponseDto buscarPorId(@PathVariable int idRegistro) {

		return mapper.toResponseDto(registroUseCase.buscarPorId(idRegistro));
	}

}