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

import com.andiana.api.aplicacion.casosuso.entrada.IInventarioProductoUseCase;
import com.andiana.api.presentacion.dto.request.InventarioProductoRequestDto;
import com.andiana.api.presentacion.dto.response.InventarioProductoResponseDto;
import com.andiana.api.presentacion.mapeadores.IInventarioProductoDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventario")
public class InventarioProductoController {

	private final IInventarioProductoUseCase inventarioProductoUseCase;
	private final IInventarioProductoDtoMapper mapper;

	public InventarioProductoController(IInventarioProductoUseCase inventarioProductoUseCase,
			IInventarioProductoDtoMapper mapper) {

		this.inventarioProductoUseCase = inventarioProductoUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public InventarioProductoResponseDto guardar(@Valid @RequestBody InventarioProductoRequestDto request) {

		return mapper.toResponseDto(inventarioProductoUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<InventarioProductoResponseDto> listarTodo() {

		return inventarioProductoUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idInventario}")
	public ResponseEntity<Void> eliminar(@PathVariable int idInventario) {

		inventarioProductoUseCase.eliminar(idInventario);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idInventario}")
	public InventarioProductoResponseDto buscarPorId(@PathVariable int idInventario) {

		return mapper.toResponseDto(inventarioProductoUseCase.buscarPorId(idInventario));
	}

}
