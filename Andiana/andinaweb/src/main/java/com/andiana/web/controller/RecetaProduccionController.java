package com.andiana.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.andiana.web.model.dto.request.RecetaProduccionRequestDto;
import com.andiana.web.service.IRecetaProduccionService;
import com.andiana.web.service.IProductoService;
import com.andiana.web.util.MensajesError;

@Controller
@RequestMapping("/receta")
public class RecetaProduccionController {

	@Autowired
	private IRecetaProduccionService servicioAPI;
	@Autowired
	private IProductoService servicioProducto;

	@GetMapping
	public String leerPagina(Model model) {
		model.addAttribute("listareceta", servicioAPI.listarReceta());
		model.addAttribute("opcionesProducto", servicioProducto.listarOpciones());
		return "/Receta/listarreceta";
	}

	@GetMapping("/nuevo")
	public String crearRecetaProduccion(Model model) {
		model.addAttribute("receta", new RecetaProduccionRequestDto());
		agregarOpciones(model);
		return "/Receta/crearreceta";
	}

	@PostMapping("/guardar")
	public String guardarRecetaProduccion(@ModelAttribute RecetaProduccionRequestDto receta, Model model) {
		try {
			servicioAPI.guardarReceta(receta);
			return "redirect:/receta";
		} catch (Exception ex) {

			model.addAttribute("receta", receta);
			model.addAttribute("error", MensajesError.extraer(ex));
			agregarOpciones(model);
			return "/Receta/crearreceta";
		}
	}

	@GetMapping("/editar/{id}")
	public String editarRecetaProduccion(@PathVariable Integer id, Model model) {
		model.addAttribute("receta", servicioAPI.buscarRecetaId(id));
		agregarOpciones(model);
		return "/Receta/crearreceta";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarRecetaProduccion(@PathVariable Integer id, RedirectAttributes flash) {
		try {
			servicioAPI.eliminarReceta(id);
		} catch (Exception ex) {

			flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
		}
		return "redirect:/receta";
	}

	private void agregarOpciones(Model model) {
		model.addAttribute("opcionesProducto", servicioProducto.listarOpciones());
	}
}
