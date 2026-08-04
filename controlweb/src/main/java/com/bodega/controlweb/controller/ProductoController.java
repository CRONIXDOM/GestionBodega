package com.bodega.controlweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bodega.controlweb.model.dto.request.ProductoRequestDto;
import com.bodega.controlweb.service.IProductoService;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private IProductoService servicioAPI;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listaproducto", servicioAPI.listarProducto());
        return "/Producto/listarproducto";
    }

    @GetMapping("/nuevo")
    public String crearProducto(Model model) {
        model.addAttribute("producto", new ProductoRequestDto());
        return "/Producto/crearproducto";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute ProductoRequestDto producto) {
        servicioAPI.guardarProducto(producto);
        return "redirect:/producto";
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Integer id, Model model) {
        model.addAttribute("producto", servicioAPI.buscarProductoId(id));
        return "/Producto/crearproducto";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id) {
        servicioAPI.eliminarProducto(id);
        return "redirect:/producto";
    }
}
