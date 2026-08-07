package com.andiana.api.presentacion.controlador;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andiana.api.aplicacion.servicio.ServicioLoteProduccion;
import com.andiana.api.dominio.modelo.LoteProduccion;

@RestController
@RequestMapping("/lote")
public class LoteProduccionControlador extends ControladorCrud<LoteProduccion> {

	public LoteProduccionControlador(ServicioLoteProduccion servicio) {
		super(servicio);
	}
}
