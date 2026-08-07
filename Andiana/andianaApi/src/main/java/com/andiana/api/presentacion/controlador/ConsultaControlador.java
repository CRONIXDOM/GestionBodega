package com.andiana.api.presentacion.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andiana.api.aplicacion.puerto.ConsultaRecetas;

/** Las dos consultas que pidio la gerencia. */
@RestController
@RequestMapping("/consulta")
public class ConsultaControlador {

	private final ConsultaRecetas consultas;

	public ConsultaControlador(ConsultaRecetas consultas) {
		this.consultas = consultas;
	}

	/** Materias primas utilizadas en una receta. */
	@GetMapping("/receta/{idReceta}/materias")
	public List<ConsultaRecetas.MateriaEnReceta> materiasDeLaReceta(@PathVariable Integer idReceta) {
		return consultas.materiasDeLaReceta(idReceta);
	}

	/** Numero de materias primas por receta. */
	@GetMapping("/recetas/conteo-materias")
	public List<ConsultaRecetas.ConteoDeReceta> conteoDeMateriasPorReceta() {
		return consultas.conteoDeMateriasPorReceta();
	}
}
