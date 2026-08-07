package com.andiana.web.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.andiana.web.cliente.ApiCliente;

/**
 * Las dos consultas que pidio la gerencia. La web no hace ningun calculo: se
 * los pide a andianaApi, que es donde vive esa logica.
 */
@Controller
public class ConsultaControlador {

	private final ApiCliente api;

	public ConsultaControlador(ApiCliente api) {
		this.api = api;
	}

	/** Consulta 1: materias primas utilizadas en una receta. */
	@GetMapping("/consulta/materias-de-receta")
	public String materiasDeReceta(@RequestParam(required = false) Integer idReceta, Model model) {
		model.addAttribute("recetas", recetasParaElSelector());
		model.addAttribute("idReceta", idReceta);

		if (idReceta != null) {
			try {
				model.addAttribute("materias", api.listar("consulta/receta/" + idReceta + "/materias"));
			} catch (Exception ex) {
				model.addAttribute("error", api.mensajeDe(ex));
			}
		}
		return "consulta-materias";
	}

	/** Consulta 2: numero de materias primas por receta. */
	@GetMapping("/consulta/conteo-materias")
	public String conteoDeMaterias(Model model) {
		model.addAttribute("conteo", api.listar("consulta/recetas/conteo-materias"));
		return "consulta-conteo";
	}

	/** El selector muestra el producto y la version, no el numero de receta. */
	private List<Map<String, Object>> recetasParaElSelector() {
		List<Map<String, Object>> productos = api.listar("producto");
		List<Map<String, Object>> opciones = new ArrayList<>();

		for (Map<String, Object> receta : api.listar("receta")) {
			String producto = productos.stream()
					.filter(p -> String.valueOf(receta.get("idProducto")).equals(String.valueOf(p.get("idProducto"))))
					.map(p -> p.get("nombre") + " " + p.get("presentacion") + " (" + p.get("volumenMl") + " ml)")
					.findFirst().orElse("(producto eliminado)");
			opciones.add(Map.of(
					"id", receta.get("idReceta"),
					"texto", producto + " - version " + receta.get("version")));
		}
		return opciones;
	}
}
