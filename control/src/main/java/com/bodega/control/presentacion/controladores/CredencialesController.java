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

import com.bodega.control.aplicacion.casosuso.entrada.ICredencialesUseCase;
import com.bodega.control.presentacion.dto.request.CredencialesRequestDto;
import com.bodega.control.presentacion.dto.response.CredencialesResponseDto;
import com.bodega.control.presentacion.mapeadores.ICredencialesDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/credenciales")
public class CredencialesController {

	private final ICredencialesUseCase credencialesUseCase;
	private final ICredencialesDtoMapper mapper;

	public CredencialesController(ICredencialesUseCase credencialesUseCase, ICredencialesDtoMapper mapper) {
		this.credencialesUseCase = credencialesUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CredencialesResponseDto guardar(@Valid @RequestBody CredencialesRequestDto request) {

		return mapper.toResponseDto(credencialesUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<CredencialesResponseDto> listarTodo() {

		return credencialesUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idCredenciales}")
	public ResponseEntity<Void> eliminar(@PathVariable int idCredenciales) {

		credencialesUseCase.eliminar(idCredenciales);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/usuario/{usuario}")
	public List<CredencialesResponseDto> buscarPorUsuario(@PathVariable String usuario) {

		return credencialesUseCase.buscarCredencialesNombre(usuario).stream().map(mapper::toResponseDto).toList();
	}

	@GetMapping("/buscar/{usuario}/{estado}")
	public List<CredencialesResponseDto> buscarUsuarioCorreo(@PathVariable String usuario,
			@PathVariable boolean estado) {

		return credencialesUseCase.buscarCredencialesEstado(usuario, estado).stream().map(mapper::toResponseDto)
				.toList();
	}

	@GetMapping("/buscarId/{idCredenciales}")
	public CredencialesResponseDto buscarPorId(@PathVariable int idCredenciales) {

		return mapper.toResponseDto(credencialesUseCase.buscarPorId(idCredenciales));
	}

}