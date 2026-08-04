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
import com.bodega.control.aplicacion.casosuso.entrada.ILoteUseCase;
import com.bodega.control.presentacion.dto.request.LoteRequestDto;
import com.bodega.control.presentacion.dto.response.LoteResponseDto;
import com.bodega.control.presentacion.mapeadores.ILoteDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/lote")
public class LoteController {

	private final ILoteUseCase loteUseCase;
	private final ILoteDtoMapper mapper;

	public LoteController(ILoteUseCase loteUseCase, ILoteDtoMapper mapper) {

		this.loteUseCase = loteUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public LoteResponseDto guardar(@Valid @RequestBody LoteRequestDto request) {

		return mapper.toResponseDto(loteUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<LoteResponseDto> listarTodo() {

		return loteUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idLote}")
	public ResponseEntity<Void> eliminar(@PathVariable int idLote) {

		loteUseCase.eliminar(idLote);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idLote}")
	public LoteResponseDto buscarPorId(@PathVariable int idLote) {

		return mapper.toResponseDto(loteUseCase.buscarPorId(idLote));
	}

}