package com.andiana.api.presentacion.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andiana.api.aplicacion.casosuso.entrada.IConsultaUseCase;
import com.andiana.api.presentacion.dto.response.ConteoRecetaDto;
import com.andiana.api.presentacion.dto.response.MateriaEnRecetaDto;

/** Las dos consultas que pidio la gerencia. */
@RestController
@RequestMapping("/consulta")
public class ConsultaController {

	private final IConsultaUseCase consultaUseCase;

	public ConsultaController(IConsultaUseCase consultaUseCase) {

		this.consultaUseCase = consultaUseCase;
	}

	/** Materias primas utilizadas en una receta. */
	@GetMapping("/receta/{idReceta}/materias")
	public List<MateriaEnRecetaDto> materiasDeLaReceta(@PathVariable int idReceta) {

		return consultaUseCase.materiasDeLaReceta(idReceta);
	}

	/** Numero de materias primas por receta. */
	@GetMapping("/recetas/conteo-materias")
	public List<ConteoRecetaDto> conteoDeMateriasPorReceta() {

		return consultaUseCase.conteoDeMateriasPorReceta();
	}

}
