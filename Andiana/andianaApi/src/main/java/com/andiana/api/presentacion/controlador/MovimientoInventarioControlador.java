package com.andiana.api.presentacion.controlador;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andiana.api.aplicacion.servicio.ServicioMovimientoInventario;
import com.andiana.api.dominio.modelo.MovimientoInventario;

@RestController
@RequestMapping("/movimiento")
public class MovimientoInventarioControlador extends ControladorCrud<MovimientoInventario> {

	public MovimientoInventarioControlador(ServicioMovimientoInventario servicio) {
		super(servicio);
	}
}
