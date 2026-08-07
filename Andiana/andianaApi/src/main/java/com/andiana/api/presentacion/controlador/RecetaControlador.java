package com.andiana.api.presentacion.controlador;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andiana.api.aplicacion.servicio.ServicioReceta;
import com.andiana.api.dominio.modelo.Receta;

@RestController
@RequestMapping("/receta")
public class RecetaControlador extends ControladorCrud<Receta> {

	public RecetaControlador(ServicioReceta servicio) {
		super(servicio);
	}
}
