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

import com.translog.api.aplicacion.casosuso.entrada.IEnvioUseCase;
import com.translog.api.presentacion.dto.request.EnvioRequestDto;
import com.translog.api.presentacion.dto.response.EnvioResponseDto;
import com.translog.api.presentacion.mapeadores.IEnvioDtoMapper;

@RestController
@RequestMapping("/envio")
public class EnvioController {

	private final IEnvioUseCase envioUseCase;
	private final IEnvioDtoMapper mapper;

	public EnvioController(IEnvioUseCase envioUseCase, IEnvioDtoMapper mapper) {
		this.envioUseCase = envioUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EnvioResponseDto guardar(@RequestBody EnvioRequestDto request) {
		return mapper.toResponseDto(envioUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<EnvioResponseDto> listarTodo() {
		return envioUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@GetMapping("/buscarId/{idEnvio}")
	public EnvioResponseDto buscarPorId(@PathVariable int idEnvio) {
		return mapper.toResponseDto(envioUseCase.buscarPorId(idEnvio));
	}

	@DeleteMapping("/{idEnvio}")
	public ResponseEntity<Void> eliminar(@PathVariable int idEnvio) {
		envioUseCase.eliminar(idEnvio);
		return ResponseEntity.noContent().build();
	}

}
