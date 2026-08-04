package com.bodega.control.presentacion.controladores;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bodega.control.aplicacion.casosuso.entrada.IReporteUseCase;
import com.bodega.control.aplicacion.casosuso.entrada.IRegistroUseCase;
import com.bodega.control.dominio.entidades.Registro;
import com.bodega.control.dominio.entidades.Ubicacion;
import com.bodega.control.dominio.entidades.UsuarioRol;
import com.bodega.control.presentacion.dto.request.ReporteRequestDto;
import com.bodega.control.presentacion.dto.response.MovimientoReporteResponseDto;
import com.bodega.control.presentacion.dto.response.ReporteResponseDto;
import com.bodega.control.presentacion.mapeadores.IReporteDtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reporte")
public class ReporteController {

	private final IReporteUseCase reporteUseCase;
	private final IReporteDtoMapper mapper;
	private final IRegistroUseCase registroUseCase;

	public ReporteController(IReporteUseCase reporteUseCase, IReporteDtoMapper mapper,
			IRegistroUseCase registroUseCase) {

		this.reporteUseCase = reporteUseCase;
		this.mapper = mapper;
		this.registroUseCase = registroUseCase;
	}

	@GetMapping("/movimientos")
	public List<MovimientoReporteResponseDto> reporteMovimientos(
			@RequestParam(required = false) LocalDate desde, @RequestParam(required = false) LocalDate hasta,
			@RequestParam(required = false) Integer idTipo, @RequestParam(required = false) Integer idSede) {

		return registroUseCase.listarTodos().stream()
				.filter(r -> desde == null || (r.getFechaRegistro() != null && !r.getFechaRegistro().isBefore(desde)))
				.filter(r -> hasta == null || (r.getFechaRegistro() != null && !r.getFechaRegistro().isAfter(hasta)))
				.filter(r -> idTipo == null || (r.getTipo() != null && idTipo.equals(r.getTipo().getIdTipo())))
				.filter(r -> idSede == null || idSede.equals(idSedeDe(r)))
				.map(this::aMovimientoDto)
				.toList();
	}

	private Integer idSedeDe(Registro registro) {
		Ubicacion ubicacion = registro.getUbicacion();
		if (ubicacion == null || ubicacion.getSede() == null) {
			return null;
		}
		return ubicacion.getSede().getIdSede();
	}

	private MovimientoReporteResponseDto aMovimientoDto(Registro registro) {
		MovimientoReporteResponseDto dto = new MovimientoReporteResponseDto();
		dto.setIdRegistro(registro.getIdRegistro());
		dto.setFechaRegistro(registro.getFechaRegistro());

		if (registro.getLote() != null) {
			dto.setNumeroLote(registro.getLote().getNumeroLote());
			if (registro.getLote().getProducto() != null) {
				dto.setNombreProducto(registro.getLote().getProducto().getNombreProducto());
			}
		}
		if (registro.getTipo() != null) {
			dto.setIdTipo(registro.getTipo().getIdTipo());
			dto.setTipoMovimiento(registro.getTipo().getDescripcion());
			dto.setClaseMovimiento(registro.getTipo().getClase());
		}
		Ubicacion ubicacion = registro.getUbicacion();
		if (ubicacion != null) {
			dto.setCodigoUbicacion(ubicacion.getCodigoUbicacion());
			if (ubicacion.getZona() != null) {
				dto.setNombreZona(ubicacion.getZona().getNombreZona());
			}
			if (ubicacion.getSede() != null) {
				dto.setIdSede(ubicacion.getSede().getIdSede());
				dto.setNombreSede(ubicacion.getSede().getNombreSede());
			}
		}
		UsuarioRol usuarioRol = registro.getUsuarioRol();
		if (usuarioRol != null && usuarioRol.getUsuario() != null) {
			String nombre = usuarioRol.getUsuario().getNombreUsuario();
			String apellido = usuarioRol.getUsuario().getApellidoUsuario();
			dto.setNombreUsuario(((nombre == null ? "" : nombre) + " " + (apellido == null ? "" : apellido)).trim());
		}
		return dto;
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