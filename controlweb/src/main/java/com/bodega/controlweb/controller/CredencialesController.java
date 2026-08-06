package com.bodega.controlweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bodega.controlweb.model.dto.request.CredencialesRequestDto;
import com.bodega.controlweb.service.ICredencialesService;
import com.bodega.controlweb.util.MensajesError;

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
    public String guardarCredenciales(@ModelAttribute CredencialesRequestDto credenciales, Model model) {
        try {
            servicioAPI.guardarCredenciales(credenciales);
            return "redirect:/credenciales";
        } catch (Exception ex) {
            // se vuelve al formulario con lo ya escrito y el motivo del rechazo,
            // en vez de mostrar la pagina de error de Spring.
            model.addAttribute("credenciales", credenciales);
            model.addAttribute("error", MensajesError.extraer(ex));
            return "/Credenciales/crearcredenciales";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarCredenciales(@PathVariable Integer id, Model model) {
        model.addAttribute("credenciales", servicioAPI.buscarCredencialesId(id));
        return "/Credenciales/crearcredenciales";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCredenciales(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            servicioAPI.eliminarCredenciales(id);
        } catch (Exception ex) {
            // normalmente pasa cuando el registro esta usado por otro (clave foranea):
            // se avisa en pantalla en vez de mostrar la pagina de error de Spring.
            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/credenciales";
    }
}
