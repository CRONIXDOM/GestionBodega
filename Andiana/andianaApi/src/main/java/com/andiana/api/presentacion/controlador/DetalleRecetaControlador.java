package com.andiana.api.presentacion.controlador;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andiana.api.aplicacion.servicio.ServicioDetalleReceta;
import com.andiana.api.dominio.modelo.DetalleReceta;

@RestController
@RequestMapping("/detalleReceta")
public class DetalleRecetaControlador extends ControladorCrud<DetalleReceta> {

	public DetalleRecetaControlador(ServicioDetalleReceta servicio) {
		super(servicio);
	}
}
