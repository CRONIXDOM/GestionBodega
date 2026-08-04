package com.bodega.controlweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bodega.controlweb.model.dto.request.ReporteRequestDto;
import com.bodega.controlweb.service.IReporteService;
import com.bodega.controlweb.service.IUsuarioRolService;

@Controller
@RequestMapping("/reporte")
public class ReporteController {

    @Autowired
    private IReporteService servicioAPI;
    @Autowired
    private IUsuarioRolService servicioUsuarioRol;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listareporte", servicioAPI.listarReporte());
        return "/Reporte/listarreporte";
    }

    @GetMapping("/nuevo")
    public String crearReporte(Model model) {
        model.addAttribute("reporte", new ReporteRequestDto());
        model.addAttribute("opcionesUsuarioRol", servicioUsuarioRol.listarOpciones());
        return "/Reporte/crearreporte";
    }

    @PostMapping("/guardar")
    public String guardarReporte(@ModelAttribute ReporteRequestDto reporte) {
        servicioAPI.guardarReporte(reporte);
        return "redirect:/reporte";
    }

    @GetMapping("/editar/{id}")
    public String editarReporte(@PathVariable Integer id, Model model) {
        model.addAttribute("reporte", servicioAPI.buscarReporteId(id));
        model.addAttribute("opcionesUsuarioRol", servicioUsuarioRol.listarOpciones());
        return "/Reporte/crearreporte";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarReporte(@PathVariable Integer id) {
        servicioAPI.eliminarReporte(id);
        return "redirect:/reporte";
    }
}
