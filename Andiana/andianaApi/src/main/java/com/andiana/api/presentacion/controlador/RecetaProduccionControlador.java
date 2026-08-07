package com.andiana.api.presentacion.controlador;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andiana.api.aplicacion.servicio.ServicioRecetaProduccion;
import com.andiana.api.dominio.modelo.RecetaProduccion;

@RestController
@RequestMapping("/receta")
public class RecetaProduccionControlador extends ControladorCrud<RecetaProduccion> {

	public RecetaProduccionControlador(ServicioRecetaProduccion servicio) {
		super(servicio);
	}
}
