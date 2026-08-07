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

import com.andiana.api.aplicacion.casosuso.entrada.ILoteProduccionUseCase;
import com.andiana.api.presentacion.dto.request.LoteProduccionRequestDto;
import com.andiana.api.presentacion.dto.response.LoteProduccionResponseDto;
import com.andiana.api.presentacion.mapeadores.ILoteProduccionDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/lote")
public class LoteProduccionController {

	private final ILoteProduccionUseCase loteProduccionUseCase;
	private final ILoteProduccionDtoMapper mapper;

	public LoteProduccionController(ILoteProduccionUseCase loteProduccionUseCase, ILoteProduccionDtoMapper mapper) {

		this.loteProduccionUseCase = loteProduccionUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public LoteProduccionResponseDto guardar(@Valid @RequestBody LoteProduccionRequestDto request) {

		return mapper.toResponseDto(loteProduccionUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<LoteProduccionResponseDto> listarTodo() {

		return loteProduccionUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idLote}")
	public ResponseEntity<Void> eliminar(@PathVariable int idLote) {

		loteProduccionUseCase.eliminar(idLote);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idLote}")
	public LoteProduccionResponseDto buscarPorId(@PathVariable int idLote) {

		return mapper.toResponseDto(loteProduccionUseCase.buscarPorId(idLote));
	}

}
