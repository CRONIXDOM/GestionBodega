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

import com.bodega.control.aplicacion.casosuso.entrada.IUsuarioRolUseCase;
import com.bodega.control.presentacion.dto.request.UsuarioRolRequestDto;
import com.bodega.control.presentacion.dto.response.UsuarioRolResponseDto;
import com.bodega.control.presentacion.mapeadores.IUsuarioRolDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarioRol")
public class UsuarioRolController {

	private final IUsuarioRolUseCase usuarioRolUseCase;
	private final IUsuarioRolDtoMapper mapper;

	public UsuarioRolController(IUsuarioRolUseCase usuarioRolUseCase, IUsuarioRolDtoMapper mapper) {

		this.usuarioRolUseCase = usuarioRolUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioRolResponseDto guardar(@Valid @RequestBody UsuarioRolRequestDto request) {

		return mapper.toResponseDto(usuarioRolUseCase.guardar(mapper.toDominio(request)));
	}

	@GetMapping
	public List<UsuarioRolResponseDto> listarTodo() {

		return usuarioRolUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idUsuarioRol}")
	public ResponseEntity<Void> eliminar(@PathVariable int idUsuarioRol) {

		usuarioRolUseCase.eliminar(idUsuarioRol);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idUsuarioRol}")
	public UsuarioRolResponseDto buscarPorId(@PathVariable int idUsuarioRol) {

		return mapper.toResponseDto(usuarioRolUseCase.buscarPorId(idUsuarioRol));
	}

}