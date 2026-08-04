package com.bodega.controlweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bodega.controlweb.model.dto.request.UsuarioRequestDto;
import com.bodega.controlweb.service.IUsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService servicioAPI;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listausuario", servicioAPI.listarUsuario());
        return "/Usuario/listarusuario";
    }

    @GetMapping("/nuevo")
    public String crearUsuario(Model model) {
        model.addAttribute("usuario", new UsuarioRequestDto());
        return "/Usuario/crearusuario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute UsuarioRequestDto usuario) {
        servicioAPI.guardarUsuario(usuario);
        return "redirect:/usuario";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Integer id, Model model) {
        model.addAttribute("usuario", servicioAPI.buscarUsuarioId(id));
        return "/Usuario/crearusuario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id) {
        servicioAPI.eliminarUsuario(id);
        return "redirect:/usuario";
    }
}
