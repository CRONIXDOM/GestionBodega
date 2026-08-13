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

import com.andiana.web.model.dto.request.InventarioProductoRequestDto;
import com.andiana.web.service.IInventarioProductoService;
import com.andiana.web.service.ILoteProduccionService;
import com.andiana.web.util.MensajesError;

@Controller
@RequestMapping("/inventario")
public class InventarioProductoController {

	@Autowired
	private IInventarioProductoService servicioAPI;
	@Autowired
	private ILoteProduccionService servicioLote;

	@GetMapping
	public String leerPagina(Model model) {
		model.addAttribute("listainventario", servicioAPI.listarInventario());
		model.addAttribute("opcionesLote", servicioLote.listarOpciones());
		return "/Inventario/listarinventario";
	}

	@GetMapping("/nuevo")
	public String crearInventarioProducto(Model model) {
		model.addAttribute("inventario", new InventarioProductoRequestDto());
		agregarOpciones(model);
		return "/Inventario/crearinventario";
	}

	@PostMapping("/guardar")
	public String guardarInventarioProducto(@ModelAttribute InventarioProductoRequestDto inventario, Model model) {
		try {
			servicioAPI.guardarInventario(inventario);
			return "redirect:/inventario";
		} catch (Exception ex) {

			model.addAttribute("inventario", inventario);
			model.addAttribute("error", MensajesError.extraer(ex));
			agregarOpciones(model);
			return "/Inventario/crearinventario";
		}
	}

	@GetMapping("/editar/{id}")
	public String editarInventarioProducto(@PathVariable Integer id, Model model) {
		model.addAttribute("inventario", servicioAPI.buscarInventarioId(id));
		agregarOpciones(model);
		return "/Inventario/crearinventario";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarInventarioProducto(@PathVariable Integer id, RedirectAttributes flash) {
		try {
			servicioAPI.eliminarInventario(id);
		} catch (Exception ex) {
			flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
		}
		return "redirect:/inventario";
	}

	private void agregarOpciones(Model model) {
		model.addAttribute("opcionesLote", servicioLote.listarOpciones());
	}
}
