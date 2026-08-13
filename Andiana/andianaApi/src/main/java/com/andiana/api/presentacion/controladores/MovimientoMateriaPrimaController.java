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

import com.andiana.api.aplicacion.casosuso.entrada.IMovimientoMateriaPrimaUseCase;
import com.andiana.api.presentacion.dto.request.MovimientoMateriaPrimaRequestDto;
import com.andiana.api.presentacion.dto.response.MovimientoMateriaPrimaResponseDto;
import com.andiana.api.presentacion.mapeadores.IMovimientoMateriaPrimaDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/movimiento")
public class MovimientoMateriaPrimaController {

	private final IMovimientoMateriaPrimaUseCase movimientoMateriaPrimaUseCase;
	private final IMovimientoMateriaPrimaDtoMapper mapper;

	public MovimientoMateriaPrimaController(IMovimientoMateriaPrimaUseCase movimientoMateriaPrimaUseCase,
			IMovimientoMateriaPrimaDtoMapper mapper) {

		this.movimientoMateriaPrimaUseCase = movimientoMateriaPrimaUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MovimientoMateriaPrimaResponseDto guardar(@Valid @RequestBody MovimientoMateriaPrimaRequestDto request) {

		return mapper.toResponseDto(movimientoMateriaPrimaUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<MovimientoMateriaPrimaResponseDto> listarTodo() {

		return movimientoMateriaPrimaUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idMovimiento}")
	public ResponseEntity<Void> eliminar(@PathVariable int idMovimiento) {

		movimientoMateriaPrimaUseCase.eliminar(idMovimiento);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idMovimiento}")
	public MovimientoMateriaPrimaResponseDto buscarPorId(@PathVariable int idMovimiento) {

		return mapper.toResponseDto(movimientoMateriaPrimaUseCase.buscarPorId(idMovimiento));
	}

}
