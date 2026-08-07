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

import com.andiana.api.aplicacion.casosuso.entrada.IRecetaProduccionUseCase;
import com.andiana.api.presentacion.dto.request.RecetaProduccionRequestDto;
import com.andiana.api.presentacion.dto.response.RecetaProduccionResponseDto;
import com.andiana.api.presentacion.mapeadores.IRecetaProduccionDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/receta")
public class RecetaProduccionController {

	private final IRecetaProduccionUseCase recetaProduccionUseCase;
	private final IRecetaProduccionDtoMapper mapper;

	public RecetaProduccionController(IRecetaProduccionUseCase recetaProduccionUseCase, IRecetaProduccionDtoMapper mapper) {

		this.recetaProduccionUseCase = recetaProduccionUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RecetaProduccionResponseDto guardar(@Valid @RequestBody RecetaProduccionRequestDto request) {

		return mapper.toResponseDto(recetaProduccionUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<RecetaProduccionResponseDto> listarTodo() {

		return recetaProduccionUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idReceta}")
	public ResponseEntity<Void> eliminar(@PathVariable int idReceta) {

		recetaProduccionUseCase.eliminar(idReceta);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idReceta}")
	public RecetaProduccionResponseDto buscarPorId(@PathVariable int idReceta) {

		return mapper.toResponseDto(recetaProduccionUseCase.buscarPorId(idReceta));
	}

}
