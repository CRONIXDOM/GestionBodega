package com.andiana.api.presentacion.controlador;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andiana.api.aplicacion.servicio.ServicioRecetaDetalle;
import com.andiana.api.dominio.modelo.RecetaDetalle;

@RestController
@RequestMapping("/recetaDetalle")
public class RecetaDetalleControlador extends ControladorCrud<RecetaDetalle> {

	public RecetaDetalleControlador(ServicioRecetaDetalle servicio) {
		super(servicio);
	}
}
