package com.andiana.api.presentacion.controlador;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andiana.api.aplicacion.servicio.ServicioAlmacenProductoTerminado;
import com.andiana.api.dominio.modelo.AlmacenProductoTerminado;

@RestController
@RequestMapping("/almacen")
public class AlmacenProductoTerminadoControlador extends ControladorCrud<AlmacenProductoTerminado> {

	public AlmacenProductoTerminadoControlador(ServicioAlmacenProductoTerminado servicio) {
		super(servicio);
	}
}
