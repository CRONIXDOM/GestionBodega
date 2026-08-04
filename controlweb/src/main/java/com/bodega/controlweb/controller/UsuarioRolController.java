package com.bodega.controlweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bodega.controlweb.model.dto.request.UsuarioRolRequestDto;
import com.bodega.controlweb.service.IRolService;
import com.bodega.controlweb.service.IUsuarioRolService;
import com.bodega.controlweb.service.IUsuarioService;

@Controller
@RequestMapping("/usuariorol")
public class UsuarioRolController {

    @Autowired
    private IUsuarioRolService servicioAPI;
    @Autowired
    private IUsuarioService servicioUsuario;
    @Autowired
    private IRolService servicioRol;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listausuariorol", servicioAPI.listarUsuarioRol());
        return "/UsuarioRol/listarusuariorol";
    }

    @GetMapping("/nuevo")
    public String crearUsuarioRol(Model model) {
        model.addAttribute("usuarioRol", new UsuarioRolRequestDto());
        model.addAttribute("opcionesUsuario", servicioUsuario.listarOpciones());
        model.addAttribute("opcionesRol", servicioRol.listarOpciones());
        return "/UsuarioRol/crearusuariorol";
    }

    @PostMapping("/guardar")
    public String guardarUsuarioRol(@ModelAttribute UsuarioRolRequestDto usuarioRol) {
        servicioAPI.guardarUsuarioRol(usuarioRol);
        return "redirect:/usuariorol";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuarioRol(@PathVariable Integer id, Model model) {
        model.addAttribute("usuarioRol", servicioAPI.buscarUsuarioRolId(id));
        model.addAttribute("opcionesUsuario", servicioUsuario.listarOpciones());
        model.addAttribute("opcionesRol", servicioRol.listarOpciones());
        return "/UsuarioRol/crearusuariorol";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuarioRol(@PathVariable Integer id) {
        servicioAPI.eliminarUsuarioRol(id);
        return "redirect:/usuariorol";
    }
}
