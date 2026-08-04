package com.bodega.controlweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bodega.controlweb.model.dto.request.CredencialesRequestDto;
import com.bodega.controlweb.service.ICredencialesService;

@Controller
@RequestMapping("/credenciales")
public class CredencialesController {

    @Autowired
    private ICredencialesService servicioAPI;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listacredenciales", servicioAPI.listarCredenciales());
        return "/Credenciales/listarcredenciales";
    }

    @GetMapping("/nuevo")
    public String crearCredenciales(Model model) {
        model.addAttribute("credenciales", new CredencialesRequestDto());
        return "/Credenciales/crearcredenciales";
    }

    @PostMapping("/guardar")
    public String guardarCredenciales(@ModelAttribute CredencialesRequestDto credenciales) {
        servicioAPI.guardarCredenciales(credenciales);
        return "redirect:/credenciales";
    }

    @GetMapping("/editar/{id}")
    public String editarCredenciales(@PathVariable Integer id, Model model) {
        model.addAttribute("credenciales", servicioAPI.buscarCredencialesId(id));
        return "/Credenciales/crearcredenciales";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCredenciales(@PathVariable Integer id) {
        servicioAPI.eliminarCredenciales(id);
        return "redirect:/credenciales";
    }
}
