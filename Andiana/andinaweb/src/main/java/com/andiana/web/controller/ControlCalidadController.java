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

import com.andiana.web.model.dto.request.ControlCalidadRequestDto;
import com.andiana.web.service.IControlCalidadService;
import com.andiana.web.service.ILoteProduccionService;
import com.andiana.web.util.MensajesError;

@Controller
@RequestMapping("/controlcalidad")
public class ControlCalidadController {

	@Autowired
	private IControlCalidadService servicioAPI;
	@Autowired
	private ILoteProduccionService servicioLote;

	@GetMapping
	public String leerPagina(Model model) {
		model.addAttribute("listacontrolcalidad", servicioAPI.listarControlCalidad());
		model.addAttribute("opcionesLote", servicioLote.listarOpciones());
		return "/Controlcalidad/listarcontrolcalidad";
	}

	@GetMapping("/nuevo")
	public String crearControlCalidad(Model model) {
		model.addAttribute("controlCalidad", new ControlCalidadRequestDto());
		agregarOpciones(model);
		return "/Controlcalidad/crearcontrolcalidad";
	}

	@PostMapping("/guardar")
	public String guardarControlCalidad(@ModelAttribute ControlCalidadRequestDto controlCalidad, Model model) {
		try {
			servicioAPI.guardarControlCalidad(controlCalidad);
			return "redirect:/controlcalidad";
		} catch (Exception ex) {
			model.addAttribute("controlCalidad", controlCalidad);
			model.addAttribute("error", MensajesError.extraer(ex));
			agregarOpciones(model);
			return "/Controlcalidad/crearcontrolcalidad";
		}
	}

	@GetMapping("/editar/{id}")
	public String editarControlCalidad(@PathVariable Integer id, Model model) {
		model.addAttribute("controlCalidad", servicioAPI.buscarControlCalidadId(id));
		agregarOpciones(model);
		return "/Controlcalidad/crearcontrolcalidad";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarControlCalidad(@PathVariable Integer id, RedirectAttributes flash) {
		try {
			servicioAPI.eliminarControlCalidad(id);
		} catch (Exception ex) {
			flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
		}
		return "redirect:/controlcalidad";
	}

	private void agregarOpciones(Model model) {
		model.addAttribute("opcionesLote", servicioLote.listarOpciones());
	}
}
