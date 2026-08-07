package com.andiana.api.presentacion.controlador;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andiana.api.aplicacion.servicio.ServicioMovimientoMateriaPrima;
import com.andiana.api.dominio.modelo.MovimientoMateriaPrima;

@RestController
@RequestMapping("/movimiento")
public class MovimientoMateriaPrimaControlador extends ControladorCrud<MovimientoMateriaPrima> {

	public MovimientoMateriaPrimaControlador(ServicioMovimientoMateriaPrima servicio) {
		super(servicio);
	}
}
