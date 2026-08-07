package com.andiana.api.presentacion.controlador;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andiana.api.aplicacion.servicio.ServicioOrdenProduccion;
import com.andiana.api.dominio.modelo.OrdenProduccion;

@RestController
@RequestMapping("/orden")
public class OrdenProduccionControlador extends ControladorCrud<OrdenProduccion> {

	public OrdenProduccionControlador(ServicioOrdenProduccion servicio) {
		super(servicio);
	}
}
