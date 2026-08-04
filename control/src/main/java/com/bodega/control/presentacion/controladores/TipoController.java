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

import com.bodega.control.aplicacion.casosuso.entrada.ITipoUseCase;
import com.bodega.control.presentacion.dto.request.TipoRequestDto;
import com.bodega.control.presentacion.dto.response.TipoResponseDto;
import com.bodega.control.presentacion.mapeadores.ITipoDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tipo")
public class TipoController {

	private final ITipoUseCase tipoUseCase;
	private final ITipoDtoMapper mapper;

	public TipoController(ITipoUseCase tipoUseCase, ITipoDtoMapper mapper) {

		this.tipoUseCase = tipoUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TipoResponseDto guardar(@Valid @RequestBody TipoRequestDto request) {

		return mapper.toResponseDto(tipoUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<TipoResponseDto> listarTodo() {

		return tipoUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idTipo}")
	public ResponseEntity<Void> eliminar(@PathVariable int idTipo) {

		tipoUseCase.eliminar(idTipo);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idTipo}")
	public TipoResponseDto buscarPorId(@PathVariable int idTipo) {

		return mapper.toResponseDto(tipoUseCase.buscarPorId(idTipo));
	}

}