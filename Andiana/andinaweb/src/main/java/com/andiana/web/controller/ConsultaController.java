package com.andiana.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.andiana.web.service.IConsultaService;
import com.andiana.web.util.MensajesError;


@Controller
@RequestMapping("/consulta")
public class ConsultaController {

    @Autowired
    private IConsultaService servicioAPI;

    @GetMapping("/materias-receta")
    public String materiasDeReceta(@RequestParam(required = false) Integer idReceta, Model model) {
        model.addAttribute("opcionesReceta", servicioAPI.recetasParaElSelector());
        model.addAttribute("idReceta", idReceta);

        if (idReceta != null) {
            try {
                model.addAttribute("listamaterias", servicioAPI.materiasDeLaReceta(idReceta));
            } catch (Exception ex) {
                model.addAttribute("error", MensajesError.extraer(ex));
            }
        }
        return "/Consulta/materiasreceta";
    }

    @GetMapping("/conteo-materias")
    public String conteoDeMaterias(Model model) {
        model.addAttribute("listaconteo", servicioAPI.conteoDeMateriasPorReceta());
        return "/Consulta/conteomaterias";
    }
}
