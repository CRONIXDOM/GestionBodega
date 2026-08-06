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

import com.bodega.controlweb.model.dto.request.LoteRequestDto;
import com.bodega.controlweb.service.ILoteService;
import com.bodega.controlweb.util.MensajesError;
import com.bodega.controlweb.service.IEtiquetasService;
import com.bodega.controlweb.service.IProductoService;
import com.bodega.controlweb.service.IUbicacionService;

@Controller
@RequestMapping("/lote")
public class LoteController {

    @Autowired
    private ILoteService servicioAPI;
    @Autowired
    private IEtiquetasService servicioEtiquetas;
    @Autowired
    private IProductoService servicioProducto;
    @Autowired
    private IUbicacionService servicioUbicacion;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("productos", servicioEtiquetas.productosPorId());
        model.addAttribute("listalote", servicioAPI.listarLote());
        return "/Lote/listarlote";
    }

    @GetMapping("/nuevo")
    public String crearLote(Model model) {
        model.addAttribute("lote", new LoteRequestDto());
        model.addAttribute("opcionesProducto", servicioProducto.listarOpciones());
        model.addAttribute("opcionesUbicacion", servicioUbicacion.listarOpciones());
        return "/Lote/crearlote";
    }

    @PostMapping("/guardar")
    public String guardarLote(@ModelAttribute LoteRequestDto lote, Model model) {
        try {
            servicioAPI.guardarLote(lote);
            return "redirect:/lote";
        } catch (Exception ex) {
            // se vuelve al formulario con lo ya escrito y el motivo del rechazo,
            // en vez de mostrar la pagina de error de Spring.
            model.addAttribute("lote", lote);
            model.addAttribute("error", MensajesError.extraer(ex));
            model.addAttribute("opcionesProducto", servicioProducto.listarOpciones());
            model.addAttribute("opcionesUbicacion", servicioUbicacion.listarOpciones());
            return "/Lote/crearlote";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarLote(@PathVariable Integer id, Model model) {
        model.addAttribute("lote", servicioAPI.buscarLoteId(id));
        model.addAttribute("opcionesProducto", servicioProducto.listarOpciones());
        model.addAttribute("opcionesUbicacion", servicioUbicacion.listarOpciones());
        return "/Lote/crearlote";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarLote(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            servicioAPI.eliminarLote(id);
        } catch (Exception ex) {
            // normalmente pasa cuando el registro esta usado por otro (clave foranea):
            // se avisa en pantalla en vez de mostrar la pagina de error de Spring.
            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/lote";
    }
}
