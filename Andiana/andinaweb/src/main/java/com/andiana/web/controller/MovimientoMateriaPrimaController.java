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

import com.andiana.web.model.dto.request.MovimientoMateriaPrimaRequestDto;
import com.andiana.web.service.IMovimientoMateriaPrimaService;
import com.andiana.web.service.IMateriaPrimaService;
import com.andiana.web.util.MensajesError;

@Controller
@RequestMapping("/movimiento")
public class MovimientoMateriaPrimaController {

	@Autowired
	private IMovimientoMateriaPrimaService servicioAPI;
	@Autowired
	private IMateriaPrimaService servicioMateriaPrima;

	@GetMapping
	public String leerPagina(Model model) {
		model.addAttribute("listamovimiento", servicioAPI.listarMovimiento());
		model.addAttribute("opcionesMateria", servicioMateriaPrima.listarOpciones());
		return "/Movimiento/listarmovimiento";
	}

	@GetMapping("/nuevo")
	public String crearMovimientoMateriaPrima(Model model) {
		model.addAttribute("movimiento", new MovimientoMateriaPrimaRequestDto());
		agregarOpciones(model);
		return "/Movimiento/crearmovimiento";
	}

	@PostMapping("/guardar")
	public String guardarMovimientoMateriaPrima(@ModelAttribute MovimientoMateriaPrimaRequestDto movimiento,
			Model model) {
		try {
			servicioAPI.guardarMovimiento(movimiento);
			return "redirect:/movimiento";
		} catch (Exception ex) {

			model.addAttribute("movimiento", movimiento);
			model.addAttribute("error", MensajesError.extraer(ex));
			agregarOpciones(model);
			return "/Movimiento/crearmovimiento";
		}
	}

	@GetMapping("/editar/{id}")
	public String editarMovimientoMateriaPrima(@PathVariable Integer id, Model model) {
		model.addAttribute("movimiento", servicioAPI.buscarMovimientoId(id));
		agregarOpciones(model);
		return "/Movimiento/crearmovimiento";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarMovimientoMateriaPrima(@PathVariable Integer id, RedirectAttributes flash) {
		try {
			servicioAPI.eliminarMovimiento(id);
		} catch (Exception ex) {

			flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
		}
		return "redirect:/movimiento";
	}

	private void agregarOpciones(Model model) {
		model.addAttribute("opcionesMateria", servicioMateriaPrima.listarOpciones());
	}
}
