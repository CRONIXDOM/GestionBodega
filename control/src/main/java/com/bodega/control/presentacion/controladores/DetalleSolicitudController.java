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
import com.bodega.control.dominio.entidades.DetalleSolicitudLote;
import com.bodega.control.presentacion.dto.request.DetalleSolicitudRequestDto;
import com.bodega.control.presentacion.dto.response.DetalleSolicitudResponseDto;
import com.bodega.control.presentacion.dto.response.LoteAsignadoResponseDto;
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

		DetalleSolicitudResponseDto guardado = mapper
				.toResponseDto(detalleSolicitudUseCase.guardar(mapper.toDomain(request), request.getIdLote()));
		return conAsignaciones(guardado);
	}

	@GetMapping
	public List<DetalleSolicitudResponseDto> listarTodo() {

		return detalleSolicitudUseCase.listarTodos().stream().map(mapper::toResponseDto)
				.map(this::conAsignaciones).toList();
	}

	@DeleteMapping("/{idDetalleSolicitud}")
	public ResponseEntity<Void> eliminar(@PathVariable int idDetalleSolicitud) {

		detalleSolicitudUseCase.eliminar(idDetalleSolicitud);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idDetalleSolicitud}")
	public DetalleSolicitudResponseDto buscarPorId(@PathVariable int idDetalleSolicitud) {

		return conAsignaciones(mapper.toResponseDto(detalleSolicitudUseCase.buscarPorId(idDetalleSolicitud)));
	}

	private DetalleSolicitudResponseDto conAsignaciones(DetalleSolicitudResponseDto dto) {
		List<DetalleSolicitudLote> asignaciones = detalleSolicitudUseCase
				.obtenerAsignaciones(dto.getIdDetalleSolicitud());
		dto.setLotesAsignados(asignaciones.stream()
				.map(a -> new LoteAsignadoResponseDto(a.getLote().getIdLote(), a.getLote().getNumeroLote(),
						a.getCantidad()))
				.toList());
		return dto;
	}

}
