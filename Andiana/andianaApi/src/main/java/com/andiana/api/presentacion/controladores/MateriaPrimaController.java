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

import com.andiana.api.aplicacion.casosuso.entrada.IMateriaPrimaUseCase;
import com.andiana.api.presentacion.dto.request.MateriaPrimaRequestDto;
import com.andiana.api.presentacion.dto.response.MateriaPrimaResponseDto;
import com.andiana.api.presentacion.mapeadores.IMateriaPrimaDtoMapper;

@RestController
@RequestMapping("/materiaPrima")
public class MateriaPrimaController {

	private final IMateriaPrimaUseCase materiaPrimaUseCase;
	private final IMateriaPrimaDtoMapper mapper;

	public MateriaPrimaController(IMateriaPrimaUseCase materiaPrimaUseCase, IMateriaPrimaDtoMapper mapper) {

		this.materiaPrimaUseCase = materiaPrimaUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MateriaPrimaResponseDto guardar(@RequestBody MateriaPrimaRequestDto request) {

		return mapper.toResponseDto(materiaPrimaUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<MateriaPrimaResponseDto> listarTodo() {

		return materiaPrimaUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idMateria}")
	public ResponseEntity<Void> eliminar(@PathVariable int idMateria) {

		materiaPrimaUseCase.eliminar(idMateria);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idMateria}")
	public MateriaPrimaResponseDto buscarPorId(@PathVariable int idMateria) {

		return mapper.toResponseDto(materiaPrimaUseCase.buscarPorId(idMateria));
	}

}
