package com.bodega.controlweb.controller;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bodega.controlweb.model.dto.request.UsuarioRolRequestDto;
import com.bodega.controlweb.model.dto.response.RolResponseDto;
import com.bodega.controlweb.model.dto.response.UsuarioResponseDto;
import com.bodega.controlweb.service.IRolService;
import com.bodega.controlweb.util.MensajesError;
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

        Map<Integer, String> nombresUsuario = servicioUsuario.listarUsuario().stream()
                .collect(Collectors.toMap(UsuarioResponseDto::getIdUsuario,
                        u -> (u.getNombreUsuario() == null ? "" : u.getNombreUsuario()) + " "
                                + (u.getApellidoUsuario() == null ? "" : u.getApellidoUsuario())));
        // Collectors.toMap revienta si el valor es null, y un rol antiguo puede
        // tener el nombre vacio: se sustituye por "" antes de armar el mapa.
        Map<Integer, String> nombresRol = servicioRol.listarRol().stream()
                .collect(Collectors.toMap(RolResponseDto::getIdRol,
                        r -> r.getNombreRol() == null ? "" : r.getNombreRol()));
        model.addAttribute("nombresUsuario", nombresUsuario);
        model.addAttribute("nombresRol", nombresRol);

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
    public String guardarUsuarioRol(@ModelAttribute UsuarioRolRequestDto usuarioRol, Model model) {
        try {
            servicioAPI.guardarUsuarioRol(usuarioRol);
            return "redirect:/usuariorol";
        } catch (Exception ex) {
            // se vuelve al formulario con lo ya escrito y el motivo del rechazo,
            // en vez de mostrar la pagina de error de Spring.
            model.addAttribute("usuarioRol", usuarioRol);
            model.addAttribute("error", MensajesError.extraer(ex));
            model.addAttribute("opcionesUsuario", servicioUsuario.listarOpciones());
            model.addAttribute("opcionesRol", servicioRol.listarOpciones());
            return "/UsuarioRol/crearusuariorol";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarUsuarioRol(@PathVariable Integer id, Model model) {
        model.addAttribute("usuarioRol", servicioAPI.buscarUsuarioRolId(id));
        model.addAttribute("opcionesUsuario", servicioUsuario.listarOpciones());
        model.addAttribute("opcionesRol", servicioRol.listarOpciones());
        return "/UsuarioRol/crearusuariorol";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuarioRol(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            servicioAPI.eliminarUsuarioRol(id);
        } catch (Exception ex) {
            // normalmente pasa cuando el registro esta usado por otro (clave foranea):
            // se avisa en pantalla en vez de mostrar la pagina de error de Spring.
            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/usuariorol";
    }
}
