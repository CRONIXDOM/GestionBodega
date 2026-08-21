package com.translog.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.translog.web.model.dto.request.DespachoRequestDto;
import com.translog.web.model.dto.response.EnvioResponseDto;
import com.translog.web.model.dto.response.OpcionSelectDto;
import com.translog.web.service.IConductorService;
import com.translog.web.service.IDespachoService;
import com.translog.web.service.IEnvioService;
import com.translog.web.service.IRutaService;
import com.translog.web.service.IVehiculoService;
import com.translog.web.util.MensajesError;

/**
 * El despacho se registra junto con los envíos que va a transportar: la regla
 * de negocio habla de las dos cosas a la vez, así que van en un solo formulario
 * y en un solo guardado.
 */
@Controller
@RequestMapping("/despacho")
public class DespachoController {

	@Autowired
	private IDespachoService servicioAPI;
	@Autowired
	private IRutaService servicioRuta;
	@Autowired
	private IVehiculoService servicioVehiculo;
	@Autowired
	private IConductorService servicioConductor;
	@Autowired
	private IEnvioService servicioEnvio;

	@GetMapping
	public String leerPagina(Model model) {
		model.addAttribute("listadespacho", servicioAPI.listarDespacho());
		model.addAttribute("rutas", servicioRuta.listarRuta());
		model.addAttribute("ciudades", servicioEnvio.listarEnvio());
		agregarCatalogos(model);
		return "/Despacho/listardespacho";
	}

	@GetMapping("/nuevo")
	public String crearDespacho(Model model) {
		model.addAttribute("despacho", new DespachoRequestDto());
		model.addAttribute("enviosDisponibles", List.of());
		agregarCatalogos(model);
		return "/Despacho/creardespacho";
	}

	/**
	 * Al elegir la ruta se vuelve a cargar el formulario con los envíos que esa
	 * ruta admite: los que van de su origen a su destino y todavía están libres.
	 * Así el usuario no puede ni intentar cargar uno que la regla rechazaría.
	 */
	@GetMapping("/envios")
	public String enviosDeLaRuta(@RequestParam Integer idRuta,
			@RequestParam(required = false) Integer idDespacho,
			@RequestParam(required = false) Integer idVehiculo,
			@RequestParam(required = false) Integer idConductor,
			@RequestParam(required = false) String fechaDespacho,
			@RequestParam(required = false) String estado, Model model) {

		DespachoRequestDto despacho = new DespachoRequestDto();
		despacho.setIdDespacho(idDespacho);
		despacho.setIdRuta(idRuta);
		despacho.setIdVehiculo(idVehiculo);
		despacho.setIdConductor(idConductor);
		despacho.setEstado(estado);
		if (fechaDespacho != null && !fechaDespacho.isBlank()) {
			despacho.setFechaDespacho(java.time.LocalDate.parse(fechaDespacho));
		}

		model.addAttribute("despacho", despacho);
		model.addAttribute("enviosDisponibles", servicioAPI.enviosDisponibles(idRuta, idDespacho));
		model.addAttribute("yaCargados", idDespacho == null ? List.of()
				: servicioAPI.enviosDelDespacho(idDespacho).stream().map(EnvioResponseDto::getIdEnvio).toList());
		agregarCatalogos(model);
		return "/Despacho/creardespacho";
	}

	@PostMapping("/guardar")
	public String guardarDespacho(@ModelAttribute DespachoRequestDto despacho,
			@RequestParam(name = "idsDeEnvios", required = false) List<Integer> idsDeEnvios, Model model) {

		despacho.setIdsDeEnvios(idsDeEnvios);
		try {
			servicioAPI.guardarDespacho(despacho);
			return "redirect:/despacho";
		} catch (Exception ex) {
			// se vuelve al formulario con lo elegido y el motivo del rechazo, que es
			// justo lo que el usuario necesita leer para corregirlo
			model.addAttribute("despacho", despacho);
			model.addAttribute("error", MensajesError.extraer(ex));
			model.addAttribute("enviosDisponibles", despacho.getIdRuta() == null ? List.of()
					: servicioAPI.enviosDisponibles(despacho.getIdRuta(), despacho.getIdDespacho()));
			model.addAttribute("yaCargados", idsDeEnvios == null ? List.of() : idsDeEnvios);
			agregarCatalogos(model);
			return "/Despacho/creardespacho";
		}
	}

	@GetMapping("/editar/{id}")
	public String editarDespacho(@PathVariable Integer id, Model model) {
		var encontrado = servicioAPI.buscarDespachoId(id);
		DespachoRequestDto despacho = new DespachoRequestDto();
		despacho.setIdDespacho(encontrado.getIdDespacho());
		despacho.setFechaDespacho(encontrado.getFechaDespacho());
		despacho.setIdRuta(encontrado.getIdRuta());
		despacho.setIdVehiculo(encontrado.getIdVehiculo());
		despacho.setIdConductor(encontrado.getIdConductor());
		despacho.setEstado(encontrado.getEstado());

		model.addAttribute("despacho", despacho);
		model.addAttribute("enviosDisponibles", servicioAPI.enviosDisponibles(encontrado.getIdRuta(), id));
		model.addAttribute("yaCargados",
				servicioAPI.enviosDelDespacho(id).stream().map(EnvioResponseDto::getIdEnvio).toList());
		agregarCatalogos(model);
		return "/Despacho/creardespacho";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarDespacho(@PathVariable Integer id, RedirectAttributes flash) {
		try {
			servicioAPI.eliminarDespacho(id);
			flash.addFlashAttribute("exito", "Despacho eliminado. Sus envíos volvieron a quedar libres.");
		} catch (Exception ex) {
			flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
		}
		return "redirect:/despacho";
	}

	/** Detalle: qué envíos lleva este despacho. */
	@GetMapping("/detalle/{id}")
	public String detalle(@PathVariable Integer id, Model model) {
		model.addAttribute("despacho", servicioAPI.buscarDespachoId(id));
		model.addAttribute("envios", servicioAPI.enviosDelDespacho(id));
		model.addAttribute("rutas", servicioRuta.listarRuta());
		agregarCatalogos(model);
		return "/Despacho/detalledespacho";
	}

	/**
	 * Los catálogos para los selectores, y de paso el nombre de cada uno por su
	 * id, para que las tablas muestren "QUITO → GUAYAQUIL" y no un número.
	 */
	private void agregarCatalogos(Model model) {
		model.addAttribute("opcionesRuta", servicioRuta.listarOpciones());
		model.addAttribute("opcionesVehiculo", servicioVehiculo.listarOpciones());
		model.addAttribute("opcionesConductor", servicioConductor.listarOpciones());
		model.addAttribute("etiquetaRuta", porId(servicioRuta.listarOpciones()));
		model.addAttribute("etiquetaVehiculo", porId(servicioVehiculo.listarOpciones()));
		model.addAttribute("etiquetaConductor", porId(servicioConductor.listarOpciones()));
	}

	private Map<Integer, String> porId(List<OpcionSelectDto> opciones) {
		Map<Integer, String> etiquetas = new HashMap<>();
		opciones.forEach(op -> etiquetas.put(op.getId(), op.getEtiqueta()));
		return etiquetas;
	}
}
