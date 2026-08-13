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

import com.andiana.web.model.dto.request.OrdenProduccionRequestDto;
import com.andiana.web.service.IOrdenProduccionService;
import com.andiana.web.service.IProductoService;
import com.andiana.web.util.MensajesError;

@Controller
@RequestMapping("/orden")
public class OrdenProduccionController {

    @Autowired
    private IOrdenProduccionService servicioAPI;
    @Autowired
    private IProductoService servicioProducto;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listaorden", servicioAPI.listarOrden());
        model.addAttribute("opcionesProducto", servicioProducto.listarOpciones());
        return "/Orden/listarorden";
    }

    @GetMapping("/nuevo")
    public String crearOrdenProduccion(Model model) {
        model.addAttribute("orden", new OrdenProduccionRequestDto());
        agregarOpciones(model);
        return "/Orden/crearorden";
    }

    @PostMapping("/guardar")
    public String guardarOrdenProduccion(@ModelAttribute OrdenProduccionRequestDto orden, Model model) {
        try {
            servicioAPI.guardarOrden(orden);
            return "redirect:/orden";
        } catch (Exception ex) {

            model.addAttribute("orden", orden);
            model.addAttribute("error", MensajesError.extraer(ex));
        agregarOpciones(model);
            return "/Orden/crearorden";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarOrdenProduccion(@PathVariable Integer id, Model model) {
        model.addAttribute("orden", servicioAPI.buscarOrdenId(id));
        agregarOpciones(model);
        return "/Orden/crearorden";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarOrdenProduccion(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            servicioAPI.eliminarOrden(id);
        } catch (Exception ex) {

            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/orden";
    }

    private void agregarOpciones(Model model) {
        model.addAttribute("opcionesProducto", servicioProducto.listarOpciones());
    }
}
