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

import com.bodega.controlweb.model.dto.request.SolicitudRequestDto;
import com.bodega.controlweb.service.ISolicitudService;
import com.bodega.controlweb.util.MensajesError;
import com.bodega.controlweb.service.IEtiquetasService;
import com.bodega.controlweb.service.IUsuarioRolService;

@Controller
@RequestMapping("/solicitud")
public class SolicitudController {

    @Autowired
    private ISolicitudService servicioAPI;
    @Autowired
    private IEtiquetasService servicioEtiquetas;
    @Autowired
    private IUsuarioRolService servicioUsuarioRol;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("usuarios", servicioEtiquetas.usuariosConRol());
        model.addAttribute("listasolicitud", servicioAPI.listarSolicitud());
        return "/Solicitud/listarsolicitud";
    }

    @GetMapping("/nuevo")
    public String crearSolicitud(Model model) {
        model.addAttribute("solicitud", new SolicitudRequestDto());
        model.addAttribute("opcionesUsuarioRol", servicioUsuarioRol.listarOpciones());
        return "/Solicitud/crearsolicitud";
    }

    @PostMapping("/guardar")
    public String guardarSolicitud(@ModelAttribute SolicitudRequestDto solicitud, Model model) {
        try {
            servicioAPI.guardarSolicitud(solicitud);
            return "redirect:/solicitud";
        } catch (Exception ex) {
            // se vuelve al formulario con lo ya escrito y el motivo del rechazo,
            // en vez de mostrar la pagina de error de Spring.
            model.addAttribute("solicitud", solicitud);
            model.addAttribute("error", MensajesError.extraer(ex));
            model.addAttribute("opcionesUsuarioRol", servicioUsuarioRol.listarOpciones());
            return "/Solicitud/crearsolicitud";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarSolicitud(@PathVariable Integer id, Model model) {
        model.addAttribute("solicitud", servicioAPI.buscarSolicitudId(id));
        model.addAttribute("opcionesUsuarioRol", servicioUsuarioRol.listarOpciones());
        return "/Solicitud/crearsolicitud";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarSolicitud(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            servicioAPI.eliminarSolicitud(id);
        } catch (Exception ex) {
            // normalmente pasa cuando el registro esta usado por otro (clave foranea):
            // se avisa en pantalla en vez de mostrar la pagina de error de Spring.
            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/solicitud";
    }
}
