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

import com.andiana.api.aplicacion.casosuso.entrada.IControlCalidadUseCase;
import com.andiana.api.presentacion.dto.request.ControlCalidadRequestDto;
import com.andiana.api.presentacion.dto.response.ControlCalidadResponseDto;
import com.andiana.api.presentacion.mapeadores.IControlCalidadDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/controlCalidad")
public class ControlCalidadController {

	private final IControlCalidadUseCase controlCalidadUseCase;
	private final IControlCalidadDtoMapper mapper;

	public ControlCalidadController(IControlCalidadUseCase controlCalidadUseCase, IControlCalidadDtoMapper mapper) {

		this.controlCalidadUseCase = controlCalidadUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ControlCalidadResponseDto guardar(@Valid @RequestBody ControlCalidadRequestDto request) {

		return mapper.toResponseDto(controlCalidadUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<ControlCalidadResponseDto> listarTodo() {

		return controlCalidadUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idControl}")
	public ResponseEntity<Void> eliminar(@PathVariable int idControl) {

		controlCalidadUseCase.eliminar(idControl);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idControl}")
	public ControlCalidadResponseDto buscarPorId(@PathVariable int idControl) {

		return mapper.toResponseDto(controlCalidadUseCase.buscarPorId(idControl));
	}

}
