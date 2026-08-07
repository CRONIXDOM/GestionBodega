package com.andiana.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.andiana.web.model.dto.request.DetalleRecetaRequestDto;
import com.andiana.web.service.IDetalleRecetaService;
import com.andiana.web.service.IRecetaProduccionService;
import com.andiana.web.service.IMateriaPrimaService;
import com.andiana.web.util.MensajesError;

@Controller
@RequestMapping("/detallereceta")
public class DetalleRecetaController {

    @Autowired
    private IDetalleRecetaService servicioAPI;
    @Autowired
    private IRecetaProduccionService servicioReceta;
    @Autowired
    private IMateriaPrimaService servicioMateriaPrima;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listadetallereceta", servicioAPI.listarDetalleReceta());
        model.addAttribute("opcionesReceta", servicioReceta.listarOpciones());
        model.addAttribute("opcionesMateria", servicioMateriaPrima.listarOpciones());
        return "/Detallereceta/listardetallereceta";
    }

    @GetMapping("/nuevo")
    public String crearDetalleReceta(Model model) {
        model.addAttribute("detalleReceta", new DetalleRecetaRequestDto());
        agregarOpciones(model);
        return "/Detallereceta/creardetallereceta";
    }

    @PostMapping("/guardar")
    public String guardarDetalleReceta(@ModelAttribute DetalleRecetaRequestDto detalleReceta, Model model) {
        try {
            servicioAPI.guardarDetalleReceta(detalleReceta);
            return "redirect:/detallereceta";
        } catch (Exception ex) {
            // se vuelve al formulario con lo ya escrito y el motivo del rechazo,
            // en vez de mostrar la página de error de Spring.
            model.addAttribute("detalleReceta", detalleReceta);
            model.addAttribute("error", MensajesError.extraer(ex));
        agregarOpciones(model);
            return "/Detallereceta/creardetallereceta";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarDetalleReceta(@PathVariable Integer id, Model model) {
        model.addAttribute("detalleReceta", servicioAPI.buscarDetalleRecetaId(id));
        agregarOpciones(model);
        return "/Detallereceta/creardetallereceta";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarDetalleReceta(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            servicioAPI.eliminarDetalleReceta(id);
        } catch (Exception ex) {
            // normalmente pasa cuando otro registro depende de este:
            // se avisa en pantalla en vez de mostrar la página de error.
            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/detallereceta";
    }

    private void agregarOpciones(Model model) {
        model.addAttribute("opcionesReceta", servicioReceta.listarOpciones());
        model.addAttribute("opcionesMateria", servicioMateriaPrima.listarOpciones());
    }
}
