package com.bodega.controlweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bodega.controlweb.model.dto.request.DetalleSolicitudRequestDto;
import com.bodega.controlweb.service.IDetalleSolicitudService;
import com.bodega.controlweb.service.IProductoService;

@Controller
@RequestMapping("/detallesolicitud")
public class DetalleSolicitudController {

    @Autowired
    private IDetalleSolicitudService servicioAPI;
    @Autowired
    private IProductoService servicioProducto;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listadetallesolicitud", servicioAPI.listarDetalleSolicitud());
        return "/DetalleSolicitud/listardetallesolicitud";
    }

    @GetMapping("/nuevo")
    public String crearDetalleSolicitud(Model model) {
        model.addAttribute("detalleSolicitud", new DetalleSolicitudRequestDto());
        model.addAttribute("opcionesProducto", servicioProducto.listarOpciones());
        return "/DetalleSolicitud/creardetallesolicitud";
    }

    @PostMapping("/guardar")
    public String guardarDetalleSolicitud(@ModelAttribute DetalleSolicitudRequestDto detalleSolicitud) {
        servicioAPI.guardarDetalleSolicitud(detalleSolicitud);
        return "redirect:/detallesolicitud";
    }

    @GetMapping("/editar/{id}")
    public String editarDetalleSolicitud(@PathVariable Integer id, Model model) {
        model.addAttribute("detalleSolicitud", servicioAPI.buscarDetalleSolicitudId(id));
        model.addAttribute("opcionesProducto", servicioProducto.listarOpciones());
        return "/DetalleSolicitud/creardetallesolicitud";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarDetalleSolicitud(@PathVariable Integer id) {
        servicioAPI.eliminarDetalleSolicitud(id);
        return "redirect:/detallesolicitud";
    }
}
