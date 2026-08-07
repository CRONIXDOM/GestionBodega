package com.andiana.api.presentacion.controlador;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andiana.api.aplicacion.servicio.ServicioControlCalidad;
import com.andiana.api.dominio.modelo.ControlCalidad;

@RestController
@RequestMapping("/controlCalidad")
public class ControlCalidadControlador extends ControladorCrud<ControlCalidad> {

	public ControlCalidadControlador(ServicioControlCalidad servicio) {
		super(servicio);
	}
}
