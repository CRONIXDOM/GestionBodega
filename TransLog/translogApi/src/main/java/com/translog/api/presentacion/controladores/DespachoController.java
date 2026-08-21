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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.translog.api.aplicacion.casosuso.entrada.IDespachoUseCase;
import com.translog.api.dominio.entidades.Despacho;
import com.translog.api.presentacion.dto.request.DespachoRequestDto;
import com.translog.api.presentacion.dto.response.DespachoResponseDto;
import com.translog.api.presentacion.dto.response.EnvioResponseDto;
import com.translog.api.presentacion.mapeadores.IDespachoDtoMapper;
import com.translog.api.presentacion.mapeadores.IEnvioDtoMapper;

@RestController
@RequestMapping("/despacho")
public class DespachoController {

	private final IDespachoUseCase despachoUseCase;
	private final IDespachoDtoMapper mapper;
	private final IEnvioDtoMapper envioMapper;

	public DespachoController(IDespachoUseCase despachoUseCase, IDespachoDtoMapper mapper,
			IEnvioDtoMapper envioMapper) {
		this.despachoUseCase = despachoUseCase;
		this.mapper = mapper;
		this.envioMapper = envioMapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DespachoResponseDto guardar(@RequestBody DespachoRequestDto request) {
		Despacho despacho = new Despacho(request.getIdDespacho(), request.getFechaDespacho(),
				request.getIdRuta(), request.getIdVehiculo(), request.getIdConductor(), request.getEstado());

		return mapper.toResponseDto(despachoUseCase.guardar(despacho, request.getIdsDeEnvios()));
	}

	@GetMapping
	public List<DespachoResponseDto> listarTodo() {
		return despachoUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@GetMapping("/buscarId/{idDespacho}")
	public DespachoResponseDto buscarPorId(@PathVariable int idDespacho) {
		return mapper.toResponseDto(despachoUseCase.buscarPorId(idDespacho));
	}

	@DeleteMapping("/{idDespacho}")
	public ResponseEntity<Void> eliminar(@PathVariable int idDespacho) {
		despachoUseCase.eliminar(idDespacho);
		return ResponseEntity.noContent().build();
	}

	/** Los envios que lleva este despacho. */
	@GetMapping("/{idDespacho}/envios")
	public List<EnvioResponseDto> enviosDelDespacho(@PathVariable int idDespacho) {
		return despachoUseCase.enviosDelDespacho(idDespacho).stream().map(envioMapper::toResponseDto).toList();
	}

	/** Los envios que hoy se podrian cargar en esa ruta. */
	@GetMapping("/envios-disponibles/{idRuta}")
	public List<EnvioResponseDto> enviosDisponibles(@PathVariable int idRuta,
			@RequestParam(required = false) Integer idDespacho) {
		return despachoUseCase.enviosDisponiblesParaLaRuta(idRuta, idDespacho).stream()
				.map(envioMapper::toResponseDto).toList();
	}

}
