package com.bodega.controlweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bodega.controlweb.model.dto.request.LoteRequestDto;
import com.bodega.controlweb.service.ILoteService;
import com.bodega.controlweb.service.IProductoService;

@Controller
@RequestMapping("/lote")
public class LoteController {

    @Autowired
    private ILoteService servicioAPI;
    @Autowired
    private IProductoService servicioProducto;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listalote", servicioAPI.listarLote());
        return "/Lote/listarlote";
    }

    @GetMapping("/nuevo")
    public String crearLote(Model model) {
        model.addAttribute("lote", new LoteRequestDto());
        model.addAttribute("opcionesProducto", servicioProducto.listarOpciones());
        return "/Lote/crearlote";
    }

    @PostMapping("/guardar")
    public String guardarLote(@ModelAttribute LoteRequestDto lote) {
        servicioAPI.guardarLote(lote);
        return "redirect:/lote";
    }

    @GetMapping("/editar/{id}")
    public String editarLote(@PathVariable Integer id, Model model) {
        model.addAttribute("lote", servicioAPI.buscarLoteId(id));
        model.addAttribute("opcionesProducto", servicioProducto.listarOpciones());
        return "/Lote/crearlote";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarLote(@PathVariable Integer id) {
        servicioAPI.eliminarLote(id);
        return "redirect:/lote";
    }
}
