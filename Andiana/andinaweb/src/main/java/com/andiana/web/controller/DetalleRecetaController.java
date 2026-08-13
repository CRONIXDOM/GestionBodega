package com.andiana.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.andiana.web.model.dto.request.DetalleRecetaRequestDto;
import com.andiana.web.service.IDetalleRecetaService;
import com.andiana.web.service.IRecetaProduccionService;
import com.andiana.web.service.IMateriaPrimaService;
import com.andiana.web.util.MensajesError;

@Controller
@RequestMapping("/detallereceta")
public class DetalleRecetaController {

	@Autowired
	private IDetalleRecetaService servicioAPI;
	@Autowired
	private IRecetaProduccionService servicioReceta;
	@Autowired
	private IMateriaPrimaService servicioMateriaPrima;

	@GetMapping
	public String leerPagina(Model model) {
		model.addAttribute("listadetallereceta", servicioAPI.listarDetalleReceta());
		model.addAttribute("opcionesReceta", servicioReceta.listarOpciones());
		model.addAttribute("opcionesMateria", servicioMateriaPrima.listarOpciones());
		return "/Detallereceta/listardetallereceta";
	}

	/**
	 * Alta: una receta lleva varias materias primas, así que el formulario arranca
	 * con una fila y el usuario agrega las que necesite.
	 */
	@GetMapping("/nuevo")
	public String crearDetalleReceta(Model model) {
		model.addAttribute("lineas", unaFilaVacia());
		agregarOpciones(model);
		return "/Detallereceta/creardetallereceta";
	}

	/**
	 * Guarda de una sola vez todas las filas del formulario. Las listas llegan
	 * alineadas: la fila i son idMateria[i] y cantidad[i].
	 */
	@PostMapping("/guardarVarias")
	public String guardarVariasDetalleReceta(@RequestParam(required = false) Integer idReceta,
			@RequestParam(name = "idMateria", required = false) List<Integer> idMateria,
			@RequestParam(name = "cantidad", required = false) List<BigDecimal> cantidad, Model model) {

		List<DetalleRecetaRequestDto> lineas = armarLineas(idReceta, idMateria, cantidad);
		try {
			servicioAPI.guardarVariasDetalleReceta(lineas);
			return "redirect:/detallereceta";
		} catch (Exception ex) {
			model.addAttribute("lineas", lineas.isEmpty() ? unaFilaVacia() : lineas);
			model.addAttribute("idRecetaElegida", idReceta);
			model.addAttribute("error", MensajesError.extraer(ex));
			agregarOpciones(model);
			return "/Detallereceta/creardetallereceta";
		}
	}

	@PostMapping("/guardar")
	public String guardarDetalleReceta(@ModelAttribute DetalleRecetaRequestDto detalleReceta, Model model) {
		try {
			servicioAPI.guardarDetalleReceta(detalleReceta);
			return "redirect:/detallereceta";
		} catch (Exception ex) {
			model.addAttribute("detalleReceta", detalleReceta);
			model.addAttribute("error", MensajesError.extraer(ex));
			agregarOpciones(model);
			return "/Detallereceta/creardetallereceta";
		}
	}

	@GetMapping("/editar/{id}")
	public String editarDetalleReceta(@PathVariable Integer id, Model model) {
		model.addAttribute("detalleReceta", servicioAPI.buscarDetalleRecetaId(id));
		agregarOpciones(model);
		return "/Detallereceta/creardetallereceta";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarDetalleReceta(@PathVariable Integer id, RedirectAttributes flash) {
		try {
			servicioAPI.eliminarDetalleReceta(id);
		} catch (Exception ex) {
			flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
		}
		return "redirect:/detallereceta";
	}

	private List<DetalleRecetaRequestDto> armarLineas(Integer idReceta, List<Integer> materias,
			List<BigDecimal> cantidades) {

		List<DetalleRecetaRequestDto> lineas = new ArrayList<>();
		if (materias == null) {
			return lineas;
		}
		for (int i = 0; i < materias.size(); i++) {
			// una fila que se agregó y quedó sin materia prima no se manda
			if (materias.get(i) == null) {
				continue;
			}
			DetalleRecetaRequestDto linea = new DetalleRecetaRequestDto();
			linea.setIdReceta(idReceta);
			linea.setIdMateria(materias.get(i));
			linea.setCantidad(cantidades != null && i < cantidades.size() ? cantidades.get(i) : null);
			lineas.add(linea);
		}
		return lineas;
	}

	private List<DetalleRecetaRequestDto> unaFilaVacia() {
		return new ArrayList<>(List.of(new DetalleRecetaRequestDto()));
	}

	private void agregarOpciones(Model model) {
		model.addAttribute("opcionesReceta", servicioReceta.listarOpciones());
		model.addAttribute("opcionesMateria", servicioMateriaPrima.listarOpciones());
	}
}
