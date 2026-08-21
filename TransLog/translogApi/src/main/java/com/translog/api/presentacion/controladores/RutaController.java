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

import com.translog.api.aplicacion.casosuso.entrada.IRutaUseCase;
import com.translog.api.presentacion.dto.request.RutaRequestDto;
import com.translog.api.presentacion.dto.response.RutaResponseDto;
import com.translog.api.presentacion.mapeadores.IRutaDtoMapper;

@RestController
@RequestMapping("/ruta")
public class RutaController {

	private final IRutaUseCase rutaUseCase;
	private final IRutaDtoMapper mapper;

	public RutaController(IRutaUseCase rutaUseCase, IRutaDtoMapper mapper) {
		this.rutaUseCase = rutaUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RutaResponseDto guardar(@RequestBody RutaRequestDto request) {
		return mapper.toResponseDto(rutaUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<RutaResponseDto> listarTodo() {
		return rutaUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@GetMapping("/buscarId/{idRuta}")
	public RutaResponseDto buscarPorId(@PathVariable int idRuta) {
		return mapper.toResponseDto(rutaUseCase.buscarPorId(idRuta));
	}

	@DeleteMapping("/{idRuta}")
	public ResponseEntity<Void> eliminar(@PathVariable int idRuta) {
		rutaUseCase.eliminar(idRuta);
		return ResponseEntity.noContent().build();
	}

}
