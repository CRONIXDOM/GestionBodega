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

import com.translog.api.aplicacion.casosuso.entrada.IVehiculoUseCase;
import com.translog.api.presentacion.dto.request.VehiculoRequestDto;
import com.translog.api.presentacion.dto.response.VehiculoResponseDto;
import com.translog.api.presentacion.mapeadores.IVehiculoDtoMapper;

@RestController
@RequestMapping("/vehiculo")
public class VehiculoController {

	private final IVehiculoUseCase vehiculoUseCase;
	private final IVehiculoDtoMapper mapper;

	public VehiculoController(IVehiculoUseCase vehiculoUseCase, IVehiculoDtoMapper mapper) {
		this.vehiculoUseCase = vehiculoUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VehiculoResponseDto guardar(@RequestBody VehiculoRequestDto request) {
		return mapper.toResponseDto(vehiculoUseCase.guardar(mapper.toDomain(request)));
	}

	@GetMapping
	public List<VehiculoResponseDto> listarTodo() {
		return vehiculoUseCase.listarTodos().stream().map(mapper::toResponseDto).toList();
	}

	@GetMapping("/buscarId/{idVehiculo}")
	public VehiculoResponseDto buscarPorId(@PathVariable int idVehiculo) {
		return mapper.toResponseDto(vehiculoUseCase.buscarPorId(idVehiculo));
	}

	@DeleteMapping("/{idVehiculo}")
	public ResponseEntity<Void> eliminar(@PathVariable int idVehiculo) {
		vehiculoUseCase.eliminar(idVehiculo);
		return ResponseEntity.noContent().build();
	}

}
