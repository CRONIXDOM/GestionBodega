package com.andiana.api.presentacion.controlador;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andiana.api.aplicacion.servicio.ServicioProducto;
import com.andiana.api.dominio.modelo.Producto;

@RestController
@RequestMapping("/producto")
public class ProductoControlador extends ControladorCrud<Producto> {

	public ProductoControlador(ServicioProducto servicio) {
		super(servicio);
	}
}
