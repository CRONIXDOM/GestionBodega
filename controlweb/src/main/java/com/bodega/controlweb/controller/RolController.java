package com.bodega.controlweb.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;

import com.bodega.controlweb.model.dto.request.RolRequestDto;
import com.bodega.controlweb.service.IRolService;
import com.bodega.controlweb.util.MensajesError;
import com.bodega.controlweb.util.CatalogoModulos;

@Controller
@RequestMapping("/rol")
public class RolController {

    @Autowired
    private IRolService servicioAPI;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listarol", servicioAPI.listarRol());
        return "/Rol/listarrol";
    }

    @GetMapping("/nuevo")
    public String crearRol(Model model) {
        model.addAttribute("rol", new RolRequestDto());
        agregarCatalogoModulos(model, Set.of());
        return "/Rol/crearrol";
    }

    @PostMapping("/guardar")
    public String guardarRol(@ModelAttribute RolRequestDto rol,
            @RequestParam(name = "modulos", required = false) List<String> modulosSeleccionados, Model model) {
        rol.setModulos(modulosSeleccionados == null ? "" : String.join(",", modulosSeleccionados));
        try {
            servicioAPI.guardarRol(rol);
            return "redirect:/rol";
        } catch (Exception ex) {
            // se vuelve al formulario con lo ya escrito y el motivo del rechazo,
            // en vez de mostrar la pagina de error de Spring.
            model.addAttribute("rol", rol);
            model.addAttribute("error", MensajesError.extraer(ex));
            agregarCatalogoModulos(model,
                    modulosSeleccionados == null ? Set.of() : Set.copyOf(modulosSeleccionados));
            return "/Rol/crearrol";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarRol(@PathVariable Integer id, Model model) {
        RolRequestDto rol = new RolRequestDto();
        var actual = servicioAPI.buscarRolId(id);
        rol.setIdRol(actual.getIdRol());
        rol.setNombreRol(actual.getNombreRol());
        rol.setDescripcionRol(actual.getDescripcionRol());
        rol.setModulos(actual.getModulos());
        model.addAttribute("rol", rol);
        agregarCatalogoModulos(model, parsearModulos(actual.getModulos()));
        return "/Rol/crearrol";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarRol(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            servicioAPI.eliminarRol(id);
        } catch (Exception ex) {
            // normalmente pasa cuando el registro esta usado por otro (clave foranea):
            // se avisa en pantalla en vez de mostrar la pagina de error de Spring.
            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/rol";
    }

    private void agregarCatalogoModulos(Model model, Set<String> modulosSeleccionados) {
        model.addAttribute("modulosPorGrupo", CatalogoModulos.agrupados());
        model.addAttribute("modulosSeleccionados", modulosSeleccionados);
    }

    private Set<String> parsearModulos(String modulos) {
        if (modulos == null || modulos.isBlank()) {
            return Set.of();
        }
        return Arrays.stream(modulos.split(",")).map(String::trim).filter(s -> !s.isEmpty())
                .collect(java.util.stream.Collectors.toSet());
    }
}
