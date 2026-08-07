package com.andiana.api.presentacion.controlador;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andiana.api.aplicacion.servicio.ServicioInventarioProducto;
import com.andiana.api.dominio.modelo.InventarioProducto;

@RestController
@RequestMapping("/inventario")
public class InventarioProductoControlador extends ControladorCrud<InventarioProducto> {

	public InventarioProductoControlador(ServicioInventarioProducto servicio) {
		super(servicio);
	}
}
