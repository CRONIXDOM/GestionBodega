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

import com.translog.api.aplicacion.casosuso.entrada.ICiudadUseCase;
import com.translog.api.presentacion.dto.request.CiudadRequestDto;
import com.translog.api.presentacion.dto.response.CiudadResponseDto;
import com.translog.api.presentacion.mapeadores.ICiudadDtoMapper;

@RestController
@RequestMapping("/ciudad")
public class CiudadController {

	private final ICiudadUseCase ciudadUseCase;
	private final ICiudadDtoMapper mapper;

	public CiudadController(ICiudadUseCase ciudadUseCase, ICiudadDtoMapper mapper) {
		this.ciudadUseCase = ciudadUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CiudadResponseDto guardar(@RequestBody CiudadRequestDto request) {
		return mapper.toResponseDto(ciudadUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<CiudadResponseDto> listarTodo() {
		return ciudadUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@GetMapping("/buscarId/{idCiudad}")
	public CiudadResponseDto buscarPorId(@PathVariable int idCiudad) {
		return mapper.toResponseDto(ciudadUseCase.buscarPorId(idCiudad));
	}

	@DeleteMapping("/{idCiudad}")
	public ResponseEntity<Void> eliminar(@PathVariable int idCiudad) {
		ciudadUseCase.eliminar(idCiudad);
		return ResponseEntity.noContent().build();
	}

}
