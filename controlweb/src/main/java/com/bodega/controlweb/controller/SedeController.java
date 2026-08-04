package com.bodega.controlweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bodega.controlweb.model.dto.request.SedeRequestDto;
import com.bodega.controlweb.service.ISedeService;

@Controller
@RequestMapping("/sede")
public class SedeController {

    @Autowired
    private ISedeService servicioAPI;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listasede", servicioAPI.listarSede());
        return "/Sede/listarsede";
    }

    @GetMapping("/nuevo")
    public String crearSede(Model model) {
        model.addAttribute("sede", new SedeRequestDto());
        return "/Sede/crearsede";
    }

    @PostMapping("/guardar")
    public String guardarSede(@ModelAttribute SedeRequestDto sede) {
        servicioAPI.guardarSede(sede);
        return "redirect:/sede";
    }

    @GetMapping("/editar/{id}")
    public String editarSede(@PathVariable Integer id, Model model) {
        model.addAttribute("sede", servicioAPI.buscarSedeId(id));
        return "/Sede/crearsede";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarSede(@PathVariable Integer id) {
        servicioAPI.eliminarSede(id);
        return "redirect:/sede";
    }
}
