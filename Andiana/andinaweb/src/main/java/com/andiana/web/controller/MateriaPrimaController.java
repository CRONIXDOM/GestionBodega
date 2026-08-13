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

import com.andiana.web.model.dto.request.MateriaPrimaRequestDto;
import com.andiana.web.service.IMateriaPrimaService;

import com.andiana.web.util.MensajesError;

@Controller
@RequestMapping("/materiaPrima")
public class MateriaPrimaController {

	@Autowired
	private IMateriaPrimaService servicioAPI;

	@GetMapping
	public String leerPagina(Model model) {
		model.addAttribute("listamateriaprima", servicioAPI.listarMateriaPrima());

		return "/Materiaprima/listarmateriaprima";
	}

	@GetMapping("/nuevo")
	public String crearMateriaPrima(Model model) {
		model.addAttribute("materiaPrima", new MateriaPrimaRequestDto());
		return "/Materiaprima/crearmateriaprima";
	}

	@PostMapping("/guardar")
	public String guardarMateriaPrima(@ModelAttribute MateriaPrimaRequestDto materiaPrima, Model model) {
		try {
			servicioAPI.guardarMateriaPrima(materiaPrima);
			return "redirect:/materiaPrima";
		} catch (Exception ex) {

			model.addAttribute("materiaPrima", materiaPrima);
			model.addAttribute("error", MensajesError.extraer(ex));
			return "/Materiaprima/crearmateriaprima";
		}
	}

	@GetMapping("/editar/{id}")
	public String editarMateriaPrima(@PathVariable Integer id, Model model) {
		model.addAttribute("materiaPrima", servicioAPI.buscarMateriaPrimaId(id));
		return "/Materiaprima/crearmateriaprima";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarMateriaPrima(@PathVariable Integer id, RedirectAttributes flash) {
		try {
			servicioAPI.eliminarMateriaPrima(id);
		} catch (Exception ex) {

			flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
		}
		return "redirect:/materiaPrima";
	}
}
