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

import com.bodega.control.aplicacion.casosuso.entrada.IUsuarioUseCase;
import com.bodega.control.presentacion.dto.request.UsuarioRequestDto;
import com.bodega.control.presentacion.dto.response.UsuarioResponseDto;
import com.bodega.control.presentacion.mapeadores.IUsuarioDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	private final IUsuarioUseCase usuarioUseCase;
	private final IUsuarioDtoMapper mapper;

	public UsuarioController(IUsuarioUseCase usuarioUseCase, IUsuarioDtoMapper mapper) {

		this.usuarioUseCase = usuarioUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioResponseDto guardar(@Valid @RequestBody UsuarioRequestDto request) {

		return mapper.toResponseDto(usuarioUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<UsuarioResponseDto> listarTodo() {

		return usuarioUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idUsuario}")
	public ResponseEntity<Void> eliminar(@PathVariable int idUsuario) {

		usuarioUseCase.eliminar(idUsuario);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idUsuario}")
	public UsuarioResponseDto buscarPorId(@PathVariable int idUsuario) {

		return mapper.toResponseDto(usuarioUseCase.buscarPorId(idUsuario));
	}

}