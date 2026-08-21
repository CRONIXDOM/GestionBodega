package com.translog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.translog.web.model.dto.request.ConductorRequestDto;
import com.translog.web.service.IConductorService;
import com.translog.web.util.MensajesError;

@Controller
@RequestMapping("/conductor")
public class ConductorController {

    @Autowired
    private IConductorService servicioAPI;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listaconductor", servicioAPI.listarConductor());
        return "/Conductor/listarconductor";
    }

    @GetMapping("/nuevo")
    public String crearConductor(Model model) {
        model.addAttribute("conductor", new ConductorRequestDto());
        agregarOpciones(model);
        return "/Conductor/crearconductor";
    }

    @PostMapping("/guardar")
    public String guardarConductor(@ModelAttribute ConductorRequestDto conductor, Model model) {
        try {
            servicioAPI.guardarConductor(conductor);
            return "redirect:/conductor";
        } catch (Exception ex) {
            // se vuelve al formulario con lo ya escrito y el motivo del rechazo
            model.addAttribute("conductor", conductor);
            model.addAttribute("error", MensajesError.extraer(ex));
            agregarOpciones(model);
            return "/Conductor/crearconductor";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarConductor(@PathVariable Integer id, Model model) {
        model.addAttribute("conductor", servicioAPI.buscarConductorId(id));
        agregarOpciones(model);
        return "/Conductor/crearconductor";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarConductor(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            servicioAPI.eliminarConductor(id);
        } catch (Exception ex) {
            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/conductor";
    }

    private void agregarOpciones(Model model) {
    }
}
