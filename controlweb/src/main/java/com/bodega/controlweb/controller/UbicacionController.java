package com.bodega.controlweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bodega.controlweb.model.dto.request.UbicacionRequestDto;
import com.bodega.controlweb.service.IUbicacionService;
import com.bodega.controlweb.service.IZonaService;

@Controller
@RequestMapping("/ubicacion")
public class UbicacionController {

    @Autowired
    private IUbicacionService servicioAPI;
    @Autowired
    private IZonaService servicioZona;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listaubicacion", servicioAPI.listarUbicacion());
        return "/Ubicacion/listarubicacion";
    }

    @GetMapping("/nuevo")
    public String crearUbicacion(Model model) {
        model.addAttribute("ubicacion", new UbicacionRequestDto());
        model.addAttribute("opcionesZona", servicioZona.listarOpciones());
        return "/Ubicacion/crearubicacion";
    }

    @PostMapping("/guardar")
    public String guardarUbicacion(@ModelAttribute UbicacionRequestDto ubicacion) {
        servicioAPI.guardarUbicacion(ubicacion);
        return "redirect:/ubicacion";
    }

    @GetMapping("/editar/{id}")
    public String editarUbicacion(@PathVariable Integer id, Model model) {
        model.addAttribute("ubicacion", servicioAPI.buscarUbicacionId(id));
        model.addAttribute("opcionesZona", servicioZona.listarOpciones());
        return "/Ubicacion/crearubicacion";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUbicacion(@PathVariable Integer id) {
        servicioAPI.eliminarUbicacion(id);
        return "redirect:/ubicacion";
    }
}
