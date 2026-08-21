package com.translog.api.presentacion.controladores;

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

import com.translog.api.aplicacion.casosuso.entrada.IConductorUseCase;
import com.translog.api.presentacion.dto.request.ConductorRequestDto;
import com.translog.api.presentacion.dto.response.ConductorResponseDto;
import com.translog.api.presentacion.mapeadores.IConductorDtoMapper;

@RestController
@RequestMapping("/conductor")
public class ConductorController {

	private final IConductorUseCase conductorUseCase;
	private final IConductorDtoMapper mapper;

	public ConductorController(IConductorUseCase conductorUseCase, IConductorDtoMapper mapper) {
		this.conductorUseCase = conductorUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ConductorResponseDto guardar(@RequestBody ConductorRequestDto request) {
		return mapper.toResponseDto(conductorUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<ConductorResponseDto> listarTodo() {
		return conductorUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@GetMapping("/buscarId/{idConductor}")
	public ConductorResponseDto buscarPorId(@PathVariable int idConductor) {
		return mapper.toResponseDto(conductorUseCase.buscarPorId(idConductor));
	}

	@DeleteMapping("/{idConductor}")
	public ResponseEntity<Void> eliminar(@PathVariable int idConductor) {
		conductorUseCase.eliminar(idConductor);
		return ResponseEntity.noContent().build();
	}

}
