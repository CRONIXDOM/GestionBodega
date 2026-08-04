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

import com.bodega.control.aplicacion.casosuso.entrada.ISedeUseCase;
import com.bodega.control.presentacion.dto.request.SedeRequestDto;
import com.bodega.control.presentacion.dto.response.SedeResponseDto;
import com.bodega.control.presentacion.mapeadores.ISedeDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/sede")
public class SedeController {

	private final ISedeUseCase sedeUseCase;
	private final ISedeDtoMapper mapper;

	public SedeController(ISedeUseCase sedeUseCase, ISedeDtoMapper mapper) {
		this.sedeUseCase = sedeUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public SedeResponseDto guardar(@Valid @RequestBody SedeRequestDto request) {
		return mapper.toResponseDto(sedeUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<SedeResponseDto> listarTodo() {
		return sedeUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idSede}")
	public ResponseEntity<Void> eliminar(@PathVariable int idSede) {
		sedeUseCase.eliminar(idSede);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idSede}")
	public SedeResponseDto buscarPorId(@PathVariable int idSede) {
		return mapper.toResponseDto(sedeUseCase.buscarPorId(idSede));
	}

}
