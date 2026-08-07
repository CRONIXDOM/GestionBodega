package com.andiana.api.presentacion.controlador;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andiana.api.aplicacion.servicio.ServicioMateriaPrima;
import com.andiana.api.dominio.modelo.MateriaPrima;

@RestController
@RequestMapping("/materiaPrima")
public class MateriaPrimaControlador extends ControladorCrud<MateriaPrima> {

	public MateriaPrimaControlador(ServicioMateriaPrima servicio) {
		super(servicio);
	}
}
