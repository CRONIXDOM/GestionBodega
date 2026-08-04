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

import com.bodega.control.aplicacion.casosuso.entrada.IUbicacionUseCase;
import com.bodega.control.presentacion.dto.request.UbicacionRequestDto;
import com.bodega.control.presentacion.dto.response.UbicacionResponseDto;
import com.bodega.control.presentacion.mapeadores.IUbicacionDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ubicacion")
public class UbicacionController {

	private final IUbicacionUseCase ubicacionUseCase;
	private final IUbicacionDtoMapper mapper;

	public UbicacionController(IUbicacionUseCase ubicacionUseCase, IUbicacionDtoMapper mapper) {

		this.ubicacionUseCase = ubicacionUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UbicacionResponseDto guardar(@Valid @RequestBody UbicacionRequestDto request) {

		return mapper.toResponseDto(ubicacionUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<UbicacionResponseDto> listarTodo() {

		return ubicacionUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idUbicacion}")
	public ResponseEntity<Void> eliminar(@PathVariable int idUbicacion) {

		ubicacionUseCase.eliminar(idUbicacion);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idUbicacion}")
	public UbicacionResponseDto buscarPorId(@PathVariable int idUbicacion) {

		return mapper.toResponseDto(ubicacionUseCase.buscarPorId(idUbicacion));
	}

}