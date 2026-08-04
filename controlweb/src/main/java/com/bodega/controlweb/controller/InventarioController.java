package com.bodega.controlweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bodega.controlweb.service.IInventarioService;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private IInventarioService servicioAPI;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listainventario", servicioAPI.listarInventario());
        return "/Inventario/listarinventario";
    }
}
