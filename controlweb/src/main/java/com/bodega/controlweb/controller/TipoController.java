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

import com.bodega.controlweb.model.dto.request.TipoRequestDto;
import com.bodega.controlweb.service.ITipoService;
import com.bodega.controlweb.util.MensajesError;

@Controller
@RequestMapping("/tipo")
public class TipoController {

    @Autowired
    private ITipoService servicioAPI;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listatipo", servicioAPI.listarTipo());
        return "/Tipo/listartipo";
    }

    @GetMapping("/nuevo")
    public String crearTipo(Model model) {
        model.addAttribute("tipo", new TipoRequestDto());
        return "/Tipo/creartipo";
    }

    @PostMapping("/guardar")
    public String guardarTipo(@ModelAttribute TipoRequestDto tipo, Model model) {
        try {
            servicioAPI.guardarTipo(tipo);
            return "redirect:/tipo";
        } catch (Exception ex) {
            // se vuelve al formulario con lo ya escrito y el motivo del rechazo,
            // en vez de mostrar la pagina de error de Spring.
            model.addAttribute("tipo", tipo);
            model.addAttribute("error", MensajesError.extraer(ex));
            return "/Tipo/creartipo";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarTipo(@PathVariable Integer id, Model model) {
        model.addAttribute("tipo", servicioAPI.buscarTipoId(id));
        return "/Tipo/creartipo";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarTipo(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            servicioAPI.eliminarTipo(id);
        } catch (Exception ex) {
            // normalmente pasa cuando el registro esta usado por otro (clave foranea):
            // se avisa en pantalla en vez de mostrar la pagina de error de Spring.
            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/tipo";
    }
}
