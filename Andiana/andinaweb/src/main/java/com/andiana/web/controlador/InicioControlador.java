package com.andiana.web.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.andiana.web.catalogo.Catalogo;
import com.andiana.web.cliente.ApiCliente;

@Controller
public class InicioControlador {

	private final ApiCliente api;

	public InicioControlador(ApiCliente api) {
		this.api = api;
	}

	@GetMapping("/")
	public String inicio(Model model) {
		model.addAttribute("productos", api.listar("producto").size());
		model.addAttribute("materias", api.listar("materiaPrima").size());
		model.addAttribute("recetas", api.listar("receta").size());
		model.addAttribute("ordenes", api.listar("orden").size());
		model.addAttribute("lotes", api.listar("lote").size());
		model.addAttribute("enAlmacen", api.listar("almacen").size());
		model.addAttribute("secciones", Catalogo.todas());
		return "inicio";
	}
}
