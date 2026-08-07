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

import com.andiana.web.model.dto.request.LoteProduccionRequestDto;
import com.andiana.web.service.ILoteProduccionService;
import com.andiana.web.service.IOrdenProduccionService;
import com.andiana.web.util.MensajesError;

@Controller
@RequestMapping("/lote")
public class LoteProduccionController {

    @Autowired
    private ILoteProduccionService servicioAPI;
    @Autowired
    private IOrdenProduccionService servicioOrden;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listalote", servicioAPI.listarLote());
        model.addAttribute("opcionesOrden", servicioOrden.listarOpciones());
        return "/Lote/listarlote";
    }

    @GetMapping("/nuevo")
    public String crearLoteProduccion(Model model) {
        model.addAttribute("lote", new LoteProduccionRequestDto());
        agregarOpciones(model);
        return "/Lote/crearlote";
    }

    @PostMapping("/guardar")
    public String guardarLoteProduccion(@ModelAttribute LoteProduccionRequestDto lote, Model model) {
        try {
            servicioAPI.guardarLote(lote);
            return "redirect:/lote";
        } catch (Exception ex) {
            // se vuelve al formulario con lo ya escrito y el motivo del rechazo,
            // en vez de mostrar la página de error de Spring.
            model.addAttribute("lote", lote);
            model.addAttribute("error", MensajesError.extraer(ex));
        agregarOpciones(model);
            return "/Lote/crearlote";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarLoteProduccion(@PathVariable Integer id, Model model) {
        model.addAttribute("lote", servicioAPI.buscarLoteId(id));
        agregarOpciones(model);
        return "/Lote/crearlote";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarLoteProduccion(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            servicioAPI.eliminarLote(id);
        } catch (Exception ex) {
            // normalmente pasa cuando otro registro depende de este:
            // se avisa en pantalla en vez de mostrar la página de error.
            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/lote";
    }

    private void agregarOpciones(Model model) {
        model.addAttribute("opcionesOrden", servicioOrden.listarOpciones());
    }
}
