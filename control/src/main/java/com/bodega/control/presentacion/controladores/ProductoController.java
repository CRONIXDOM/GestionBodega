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

import com.bodega.control.aplicacion.casosuso.entrada.IProductoUseCase;
import com.bodega.control.presentacion.dto.request.ProductoRequestDto;
import com.bodega.control.presentacion.dto.response.ProductoResponseDto;
import com.bodega.control.presentacion.mapeadores.IProductoDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/producto")
public class ProductoController {

	private final IProductoUseCase productoUseCase;
	private final IProductoDtoMapper mapper;

	public ProductoController(IProductoUseCase productoUseCase, IProductoDtoMapper mapper) {

		this.productoUseCase = productoUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductoResponseDto guardar(@Valid @RequestBody ProductoRequestDto request) {

		return mapper.toResponseDto(productoUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<ProductoResponseDto> listarTodo() {

		return productoUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idProducto}")
	public ResponseEntity<Void> eliminar(@PathVariable int idProducto) {

		productoUseCase.eliminar(idProducto);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idProducto}")
	public ProductoResponseDto buscarPorId(@PathVariable int idProducto) {

		return mapper.toResponseDto(productoUseCase.buscarPorId(idProducto));
	}

}