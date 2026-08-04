package com.bodega.controlweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bodega.controlweb.model.dto.request.DetalleEntregaRequestDto;
import com.bodega.controlweb.service.IDetalleEntregaService;
import com.bodega.controlweb.service.IEntregaService;

@Controller
@RequestMapping("/detalleentrega")
public class DetalleEntregaController {

    @Autowired
    private IDetalleEntregaService servicioAPI;
    @Autowired
    private IEntregaService servicioEntrega;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listadetalleentrega", servicioAPI.listarDetalleEntrega());
        return "/DetalleEntrega/listardetalleentrega";
    }

    @GetMapping("/nuevo")
    public String crearDetalleEntrega(Model model) {
        model.addAttribute("detalleEntrega", new DetalleEntregaRequestDto());
        model.addAttribute("opcionesEntrega", servicioEntrega.listarOpciones());
        return "/DetalleEntrega/creardetalleentrega";
    }

    @PostMapping("/guardar")
    public String guardarDetalleEntrega(@ModelAttribute DetalleEntregaRequestDto detalleEntrega) {
        servicioAPI.guardarDetalleEntrega(detalleEntrega);
        return "redirect:/detalleentrega";
    }

    @GetMapping("/editar/{id}")
    public String editarDetalleEntrega(@PathVariable Integer id, Model model) {
        model.addAttribute("detalleEntrega", servicioAPI.buscarDetalleEntregaId(id));
        model.addAttribute("opcionesEntrega", servicioEntrega.listarOpciones());
        return "/DetalleEntrega/creardetalleentrega";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarDetalleEntrega(@PathVariable Integer id) {
        servicioAPI.eliminarDetalleEntrega(id);
        return "redirect:/detalleentrega";
    }
}
