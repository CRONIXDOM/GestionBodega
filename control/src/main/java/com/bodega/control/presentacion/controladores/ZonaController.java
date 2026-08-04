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

import com.bodega.control.aplicacion.casosuso.entrada.IZonaUseCase;
import com.bodega.control.presentacion.dto.request.ZonaRequestDto;
import com.bodega.control.presentacion.dto.response.ZonaResponseDto;
import com.bodega.control.presentacion.mapeadores.IZonaDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/zona")
public class ZonaController {

	private final IZonaUseCase zonaUseCase;
	private final IZonaDtoMapper mapper;

	public ZonaController(IZonaUseCase zonaUseCase, IZonaDtoMapper mapper) {

		this.zonaUseCase = zonaUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ZonaResponseDto guardar(@Valid @RequestBody ZonaRequestDto request) {

		return mapper.toResponseDto(zonaUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<ZonaResponseDto> listarTodo() {

		return zonaUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idZona}")
	public ResponseEntity<Void> eliminar(@PathVariable int idZona) {

		zonaUseCase.eliminar(idZona);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idZona}")
	public ZonaResponseDto buscarPorId(@PathVariable int idZona) {

		return mapper.toResponseDto(zonaUseCase.buscarPorId(idZona));
	}

}