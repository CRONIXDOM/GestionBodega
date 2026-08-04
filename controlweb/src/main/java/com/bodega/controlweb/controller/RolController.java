package com.bodega.controlweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bodega.controlweb.model.dto.request.RolRequestDto;
import com.bodega.controlweb.service.IRolService;

@Controller
@RequestMapping("/rol")
public class RolController {

    @Autowired
    private IRolService servicioAPI;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listarol", servicioAPI.listarRol());
        return "/Rol/listarrol";
    }

    @GetMapping("/nuevo")
    public String crearRol(Model model) {
        model.addAttribute("rol", new RolRequestDto());
        return "/Rol/crearrol";
    }

    @PostMapping("/guardar")
    public String guardarRol(@ModelAttribute RolRequestDto rol) {
        servicioAPI.guardarRol(rol);
        return "redirect:/rol";
    }

    @GetMapping("/editar/{id}")
    public String editarRol(@PathVariable Integer id, Model model) {
        model.addAttribute("rol", servicioAPI.buscarRolId(id));
        return "/Rol/crearrol";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarRol(@PathVariable Integer id) {
        servicioAPI.eliminarRol(id);
        return "redirect:/rol";
    }
}
