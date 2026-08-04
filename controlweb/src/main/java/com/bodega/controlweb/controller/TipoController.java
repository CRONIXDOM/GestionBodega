package com.bodega.controlweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bodega.controlweb.model.dto.request.TipoRequestDto;
import com.bodega.controlweb.service.ITipoService;

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
    public String guardarTipo(@ModelAttribute TipoRequestDto tipo) {
        servicioAPI.guardarTipo(tipo);
        return "redirect:/tipo";
    }

    @GetMapping("/editar/{id}")
    public String editarTipo(@PathVariable Integer id, Model model) {
        model.addAttribute("tipo", servicioAPI.buscarTipoId(id));
        return "/Tipo/creartipo";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarTipo(@PathVariable Integer id) {
        servicioAPI.eliminarTipo(id);
        return "redirect:/tipo";
    }
}
