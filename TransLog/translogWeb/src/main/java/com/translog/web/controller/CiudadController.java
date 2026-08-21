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

import com.translog.web.model.dto.request.CiudadRequestDto;
import com.translog.web.service.ICiudadService;
import com.translog.web.util.MensajesError;

@Controller
@RequestMapping("/ciudad")
public class CiudadController {

    @Autowired
    private ICiudadService servicioAPI;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listaciudad", servicioAPI.listarCiudad());
        return "/Ciudad/listarciudad";
    }

    @GetMapping("/nuevo")
    public String crearCiudad(Model model) {
        model.addAttribute("ciudad", new CiudadRequestDto());
        agregarOpciones(model);
        return "/Ciudad/crearciudad";
    }

    @PostMapping("/guardar")
    public String guardarCiudad(@ModelAttribute CiudadRequestDto ciudad, Model model) {
        try {
            servicioAPI.guardarCiudad(ciudad);
            return "redirect:/ciudad";
        } catch (Exception ex) {
            // se vuelve al formulario con lo ya escrito y el motivo del rechazo
            model.addAttribute("ciudad", ciudad);
            model.addAttribute("error", MensajesError.extraer(ex));
            agregarOpciones(model);
            return "/Ciudad/crearciudad";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarCiudad(@PathVariable Integer id, Model model) {
        model.addAttribute("ciudad", servicioAPI.buscarCiudadId(id));
        agregarOpciones(model);
        return "/Ciudad/crearciudad";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCiudad(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            servicioAPI.eliminarCiudad(id);
        } catch (Exception ex) {
            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/ciudad";
    }

    private void agregarOpciones(Model model) {
    }
}
