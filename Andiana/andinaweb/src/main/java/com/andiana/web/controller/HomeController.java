package com.andiana.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.andiana.web.service.IDetalleRecetaService;
import com.andiana.web.service.IMateriaPrimaService;
import com.andiana.web.service.IProductoService;
import com.andiana.web.service.IRecetaProduccionService;

@Controller
public class HomeController {

	@Autowired
	private IProductoService servicioProducto;
	@Autowired
	private IMateriaPrimaService servicioMateria;
	@Autowired
	private IRecetaProduccionService servicioReceta;
	@Autowired
	private IDetalleRecetaService servicioDetalle;

	@GetMapping("/")
	public String leerPagina(Model model) {
		model.addAttribute("productos", servicioProducto.listarProducto().size());
		model.addAttribute("materias", servicioMateria.listarMateriaPrima().size());
		model.addAttribute("recetas", servicioReceta.listarReceta().size());
		model.addAttribute("lineasReceta", servicioDetalle.listarDetalleReceta().size());
		return "/Home/home";
	}
}
