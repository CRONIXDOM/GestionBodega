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

import com.bodega.control.aplicacion.casosuso.entrada.IReporteUseCase;
import com.bodega.control.presentacion.dto.request.ReporteRequestDto;
import com.bodega.control.presentacion.dto.response.ReporteResponseDto;
import com.bodega.control.presentacion.mapeadores.IReporteDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reporte")
public class ReporteController {

	private final IReporteUseCase reporteUseCase;
	private final IReporteDtoMapper mapper;

	public ReporteController(IReporteUseCase reporteUseCase, IReporteDtoMapper mapper) {

		this.reporteUseCase = reporteUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ReporteResponseDto guardar(@Valid @RequestBody ReporteRequestDto request) {

		return mapper.toResponseDto(reporteUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<ReporteResponseDto> listarTodo() {

		return reporteUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@DeleteMapping("/{idReporte}")
	public ResponseEntity<Void> eliminar(@PathVariable int idReporte) {

		reporteUseCase.eliminar(idReporte);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscarId/{idReporte}")
	public ReporteResponseDto buscarPorId(@PathVariable int idReporte) {

		return mapper.toResponseDto(reporteUseCase.buscarPorId(idReporte));
	}

}