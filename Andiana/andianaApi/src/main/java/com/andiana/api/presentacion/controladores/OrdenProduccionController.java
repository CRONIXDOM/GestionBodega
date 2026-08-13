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

import com.andiana.api.aplicacion.casosuso.entrada.IOrdenProduccionUseCase;
import com.andiana.api.presentacion.dto.request.OrdenProduccionRequestDto;
import com.andiana.api.presentacion.dto.response.OrdenProduccionResponseDto;
import com.andiana.api.presentacion.mapeadores.IOrdenProduccionDtoMapper;

@RestController
@RequestMapping("/orden")
public class OrdenProduccionController {

	private final IOrdenProduccionUseCase ordenProduccionUseCase;
	private final IOrdenProduccionDtoMapper mapper;

	public OrdenProduccionController(IOrdenProduccionUseCase ordenProduccionUseCase, IOrdenProduccionDtoMapper mapper) {

		this.ordenProduccionUseCase = ordenProduccionUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdenProduccionResponseDto guardar(@RequestBody OrdenProduccionRequestDto request) {

		return mapper.toResponseDto(ordenProduccionUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<OrdenProduccionResponseDto> listarTodo() {

		return ordenProduccionUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idOrden}")
	public ResponseEntity<Void> eliminar(@PathVariable int idOrden) {

		ordenProduccionUseCase.eliminar(idOrden);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idOrden}")
	public OrdenProduccionResponseDto buscarPorId(@PathVariable int idOrden) {

		return mapper.toResponseDto(ordenProduccionUseCase.buscarPorId(idOrden));
	}

}
