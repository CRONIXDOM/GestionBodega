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
import com.bodega.control.aplicacion.casosuso.entrada.IDetalleSolicitudUseCase;
import com.bodega.control.presentacion.dto.request.DetalleSolicitudRequestDto;
import com.bodega.control.presentacion.dto.response.DetalleSolicitudResponseDto;
import com.bodega.control.presentacion.mapeadores.IDetalleSolicitudDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/detalleSolicitud")
public class DetalleSolicitudController {

	private final IDetalleSolicitudUseCase detalleSolicitudUseCase;
	private final IDetalleSolicitudDtoMapper mapper;

	public DetalleSolicitudController(IDetalleSolicitudUseCase detalleSolicitudUseCase,
			IDetalleSolicitudDtoMapper mapper) {

		this.detalleSolicitudUseCase = detalleSolicitudUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DetalleSolicitudResponseDto guardar(@Valid @RequestBody DetalleSolicitudRequestDto request) {

		return mapper.toResponseDto(detalleSolicitudUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<DetalleSolicitudResponseDto> listarTodo() {

		return detalleSolicitudUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idDetalleSolicitud}")
	public ResponseEntity<Void> eliminar(@PathVariable int idDetalleSolicitud) {

		detalleSolicitudUseCase.eliminar(idDetalleSolicitud);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idDetalleSolicitud}")
	public DetalleSolicitudResponseDto buscarPorId(@PathVariable int idDetalleSolicitud) {

		return mapper.toResponseDto(detalleSolicitudUseCase.buscarPorId(idDetalleSolicitud));
	}

}