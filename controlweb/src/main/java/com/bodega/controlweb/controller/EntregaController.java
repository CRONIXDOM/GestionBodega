package com.bodega.controlweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bodega.controlweb.model.dto.request.EntregaRequestDto;
import com.bodega.controlweb.service.IEntregaService;

@Controller
@RequestMapping("/entrega")
public class EntregaController {

    @Autowired
    private IEntregaService servicioAPI;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listaentrega", servicioAPI.listarEntrega());
        return "/Entrega/listarentrega";
    }

    @GetMapping("/nuevo")
    public String crearEntrega(Model model) {
        model.addAttribute("entrega", new EntregaRequestDto());
        return "/Entrega/crearentrega";
    }

    @PostMapping("/guardar")
    public String guardarEntrega(@ModelAttribute EntregaRequestDto entrega) {
        servicioAPI.guardarEntrega(entrega);
        return "redirect:/entrega";
    }

    @GetMapping("/editar/{id}")
    public String editarEntrega(@PathVariable Integer id, Model model) {
        model.addAttribute("entrega", servicioAPI.buscarEntregaId(id));
        return "/Entrega/crearentrega";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEntrega(@PathVariable Integer id) {
        servicioAPI.eliminarEntrega(id);
        return "redirect:/entrega";
    }
}
