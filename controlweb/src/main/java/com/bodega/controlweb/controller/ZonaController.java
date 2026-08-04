package com.bodega.controlweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bodega.controlweb.model.dto.request.ZonaRequestDto;
import com.bodega.controlweb.service.IZonaService;

@Controller
@RequestMapping("/zona")
public class ZonaController {

    @Autowired
    private IZonaService servicioAPI;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listazona", servicioAPI.listarZona());
        return "/Zona/listarzona";
    }

    @GetMapping("/nuevo")
    public String crearZona(Model model) {
        model.addAttribute("zona", new ZonaRequestDto());
        return "/Zona/crearzona";
    }

    @PostMapping("/guardar")
    public String guardarZona(@ModelAttribute ZonaRequestDto zona) {
        servicioAPI.guardarZona(zona);
        return "redirect:/zona";
    }

    @GetMapping("/editar/{id}")
    public String editarZona(@PathVariable Integer id, Model model) {
        model.addAttribute("zona", servicioAPI.buscarZonaId(id));
        return "/Zona/crearzona";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarZona(@PathVariable Integer id) {
        servicioAPI.eliminarZona(id);
        return "redirect:/zona";
    }
}
