package com.andiana.web.controlador;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.andiana.web.catalogo.Campo;
import com.andiana.web.catalogo.Catalogo;
import com.andiana.web.catalogo.Seccion;
import com.andiana.web.cliente.ApiCliente;

/**
 * Un solo controlador atiende las nueve pantallas de gestion. Lo que cambia de
 * una a otra esta descrito en el {@link Catalogo}, asi que aqui solo queda el
 * comportamiento comun: listar, abrir el formulario, guardar y eliminar.
 */
@Controller
public class GestionControlador {

	private final ApiCliente api;

	public GestionControlador(ApiCliente api) {
		this.api = api;
	}

	@GetMapping("/{clave}")
	public String listar(@PathVariable String clave, Model model) {
		Seccion seccion = Catalogo.buscar(clave);
		model.addAttribute("seccion", seccion);
		model.addAttribute("filas", api.listar(seccion.rutaApi()));
		model.addAttribute("etiquetas", etiquetasRelacionadas(seccion));
		return "lista";
	}

	@GetMapping("/{clave}/nuevo")
	public String nuevo(@PathVariable String clave, Model model) {
		prepararFormulario(Catalogo.buscar(clave), new LinkedHashMap<>(), model);
		return "formulario";
	}

	@GetMapping("/{clave}/editar/{id}")
	public String editar(@PathVariable String clave, @PathVariable String id, Model model) {
		Seccion seccion = Catalogo.buscar(clave);
		prepararFormulario(seccion, api.buscar(seccion.rutaApi(), id), model);
		return "formulario";
	}

	@PostMapping("/{clave}/guardar")
	public String guardar(@PathVariable String clave, @RequestParam Map<String, String> formulario,
			Model model, RedirectAttributes flash) {
		Seccion seccion = Catalogo.buscar(clave);
		Map<String, Object> datos = aDatosDeLaApi(seccion, formulario);
		try {
			api.guardar(seccion.rutaApi(), datos);
			flash.addFlashAttribute("exito", "Se guardo " + seccion.singular() + " correctamente.");
			return "redirect:/" + clave;
		} catch (Exception ex) {
			// se vuelve al formulario con lo que ya estaba escrito y el motivo del
			// rechazo, en vez de mostrar una pagina de error
			model.addAttribute("error", api.mensajeDe(ex));
			prepararFormulario(seccion, datos, model);
			return "formulario";
		}
	}

	@GetMapping("/{clave}/eliminar/{id}")
	public String eliminar(@PathVariable String clave, @PathVariable String id, RedirectAttributes flash) {
		Seccion seccion = Catalogo.buscar(clave);
		try {
			api.eliminar(seccion.rutaApi(), id);
			flash.addFlashAttribute("exito", "Se elimino " + seccion.singular() + ".");
		} catch (Exception ex) {
			flash.addFlashAttribute("error", api.mensajeDe(ex));
		}
		return "redirect:/" + clave;
	}

	private void prepararFormulario(Seccion seccion, Map<String, Object> fila, Model model) {
		model.addAttribute("seccion", seccion);
		model.addAttribute("fila", fila);
		model.addAttribute("opciones", opcionesDeRelaciones(seccion));
	}

	/**
	 * Del formulario todo llega como texto. Aqui se convierte a lo que espera la
	 * API: numeros como numeros y los campos vacios como null, para que el
	 * backend pueda decir "este campo es obligatorio" en vez de guardar "".
	 */
	private Map<String, Object> aDatosDeLaApi(Seccion seccion, Map<String, String> formulario) {
		Map<String, Object> datos = new LinkedHashMap<>();

		String id = formulario.get(seccion.clavePrimaria());
		if (id != null && !id.isBlank()) {
			datos.put(seccion.clavePrimaria(), Integer.valueOf(id));
		}

		for (Campo campo : seccion.camposEditables()) {
			String valor = formulario.get(campo.nombre());
			if (valor == null || valor.isBlank()) {
				datos.put(campo.nombre(), campo.tipo() == Campo.Tipo.SI_NO ? Boolean.FALSE : null);
				continue;
			}
			datos.put(campo.nombre(), switch (campo.tipo()) {
				case NUMERO, RELACION -> Integer.valueOf(valor);
				case DECIMAL -> new java.math.BigDecimal(valor);
				case SI_NO -> Boolean.TRUE;
				// el input datetime-local manda "2026-08-01T08:00", que es justo el
				// formato que la API espera para un LocalDateTime
				default -> valor;
			});
		}
		return datos;
	}

	/** Las listas de los desplegables: id + texto que ve el usuario. */
	private Map<String, List<Map<String, Object>>> opcionesDeRelaciones(Seccion seccion) {
		Map<String, List<Map<String, Object>>> opciones = new LinkedHashMap<>();
		for (String clave : seccion.seccionesRelacionadas()) {
			Seccion otra = Catalogo.buscar(clave);
			List<Map<String, Object>> lista = new ArrayList<>();
			for (Map<String, Object> fila : api.listar(otra.rutaApi())) {
				lista.add(Map.of("id", fila.get(otra.clavePrimaria()), "texto", textoDe(otra, fila)));
			}
			opciones.put(clave, lista);
		}
		return opciones;
	}

	/**
	 * En las tablas se muestran nombres y no numeros: para cada campo que apunta
	 * a otra seccion se arma un diccionario id -> texto.
	 */
	private Map<String, Map<String, String>> etiquetasRelacionadas(Seccion seccion) {
		Map<String, Map<String, String>> etiquetas = new LinkedHashMap<>();
		for (String clave : seccion.seccionesRelacionadas()) {
			Seccion otra = Catalogo.buscar(clave);
			Map<String, String> porId = new LinkedHashMap<>();
			for (Map<String, Object> fila : api.listar(otra.rutaApi())) {
				porId.put(String.valueOf(fila.get(otra.clavePrimaria())), textoDe(otra, fila));
			}
			etiquetas.put(clave, porId);
		}
		return etiquetas;
	}

	/**
	 * Como se nombra una fila cuando se la menciona desde otra pantalla. Una
	 * receta se reconoce por el producto al que pertenece, no por su numero, asi
	 * que hay que ir a buscar el nombre de ese producto.
	 */
	private String textoDe(Seccion seccion, Map<String, Object> fila) {
		Object etiqueta = fila.get(seccion.etiqueta());
		String texto = etiqueta == null ? "" : etiqueta.toString();

		switch (seccion.clave()) {
			// un producto se reconoce por su sabor Y su presentacion
			case "producto" -> texto = texto + " " + fila.getOrDefault("presentacion", "")
					+ " (" + fila.getOrDefault("volumenMl", "?") + " ml)";
			// una receta, por el producto al que pertenece y su version
			case "receta" -> texto = nombreDelProducto(fila.get("idProducto")) + " - version " + texto;
			// una orden no tiene codigo propio en la base: se la nombra por su
			// producto y su fecha, que es como la reconoce planificacion
			case "orden" -> texto = "Orden #" + fila.get("idOrden") + " - "
					+ nombreDelProducto(fila.get("idProducto")) + " - " + fila.getOrDefault("fechaProgramada", "");
			default -> {
				// el resto ya tiene un codigo propio que lo identifica
			}
		}
		return texto.isBlank() ? String.valueOf(fila.get(seccion.clavePrimaria())) : texto;
	}

	private String nombreDelProducto(Object idProducto) {
		if (idProducto == null) {
			return "(sin producto)";
		}
		return api.listar("producto").stream()
				.filter(p -> String.valueOf(idProducto).equals(String.valueOf(p.get("idProducto"))))
				.map(p -> p.get("nombre") + " " + p.get("presentacion"))
				.findFirst().orElse("(producto eliminado)");
	}

	/** El resumen del panel de inicio. */
	public int cuantosHay(String rutaApi) {
		return api.listar(rutaApi).size();
	}
}
