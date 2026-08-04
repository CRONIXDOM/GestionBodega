package com.bodega.controlweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bodega.controlweb.model.dto.request.CredencialesRequestDto;
import com.bodega.controlweb.model.dto.request.UsuarioRequestDto;
import com.bodega.controlweb.model.dto.request.UsuarioRolRequestDto;
import com.bodega.controlweb.model.dto.response.UsuarioResponseDto;
import com.bodega.controlweb.service.ICredencialesService;
import com.bodega.controlweb.service.IRolService;
import com.bodega.controlweb.service.IUsuarioRolService;
import com.bodega.controlweb.service.IUsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService servicioAPI;
    @Autowired
    private ICredencialesService servicioCredenciales;
    @Autowired
    private IUsuarioRolService servicioUsuarioRol;
    @Autowired
    private IRolService servicioRol;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listausuario", servicioAPI.listarUsuario());
        return "/Usuario/listarusuario";
    }

    @GetMapping("/nuevo")
    public String crearUsuario(Model model) {
        model.addAttribute("usuario", new UsuarioRequestDto());
        model.addAttribute("opcionesRol", servicioRol.listarOpciones());
        return "/Usuario/crearusuario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute UsuarioRequestDto usuario) {
        boolean esNuevo = usuario.getIdUsuario() == null;
        UsuarioResponseDto guardado = servicioAPI.guardarUsuario(usuario);
        if (esNuevo && usuario.getContrasena() != null && !usuario.getContrasena().isBlank()) {
            CredencialesRequestDto credenciales = new CredencialesRequestDto();
            credenciales.setUsuario(usuario.getNombreUsuario());
            credenciales.setCorreo(usuario.getCorreo());
            credenciales.setContrasena(usuario.getContrasena());
            servicioCredenciales.guardarCredenciales(credenciales);

            if (usuario.getIdRol() != null) {
                UsuarioRolRequestDto usuarioRol = new UsuarioRolRequestDto();
                usuarioRol.setIdUsuario(guardado.getIdUsuario());
                usuarioRol.setIdRol(usuario.getIdRol());
                usuarioRol.setFechaAsignacion(java.time.LocalDate.now());
                servicioUsuarioRol.guardarUsuarioRol(usuarioRol);
            }
        }
        return "redirect:/usuario";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Integer id, Model model) {
        model.addAttribute("usuario", servicioAPI.buscarUsuarioId(id));
        model.addAttribute("opcionesRol", servicioRol.listarOpciones());
        return "/Usuario/crearusuario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id) {
        servicioAPI.eliminarUsuario(id);
        return "redirect:/usuario";
    }
}
