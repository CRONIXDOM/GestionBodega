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

import com.bodega.controlweb.model.dto.request.DetalleEntregaRequestDto;
import com.bodega.controlweb.service.IDetalleEntregaService;
import com.bodega.controlweb.service.IEtiquetasService;
import com.bodega.controlweb.service.IDetalleSolicitudService;
import com.bodega.controlweb.service.IEntregaService;
import com.bodega.controlweb.service.ILoteService;
import com.bodega.controlweb.service.IProductoService;
import com.bodega.controlweb.util.MensajesError;

@Controller
@RequestMapping("/detalleentrega")
public class DetalleEntregaController {

    @Autowired
    private IDetalleEntregaService servicioAPI;
    @Autowired
    private IEtiquetasService servicioEtiquetas;
    @Autowired
    private IEntregaService servicioEntrega;
    @Autowired
    private IProductoService servicioProducto;
    @Autowired
    private IDetalleSolicitudService servicioDetalleSolicitud;
    @Autowired
    private ILoteService servicioLote;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("productos", servicioEtiquetas.productosPorId());
        model.addAttribute("listadetalleentrega", servicioAPI.listarDetalleEntrega());
        return "/DetalleEntrega/listardetalleentrega";
    }

    @GetMapping("/nuevo")
    public String crearDetalleEntrega(Model model) {
        model.addAttribute("detalleEntrega", new DetalleEntregaRequestDto());
        model.addAttribute("opcionesEntrega", servicioEntrega.listarOpciones());
        model.addAttribute("opcionesProducto", servicioProducto.listarOpciones());
        model.addAttribute("opcionesDetalleSolicitud", servicioDetalleSolicitud.listarOpciones());
        model.addAttribute("opcionesLote", servicioLote.listarOpciones());
        return "/DetalleEntrega/creardetalleentrega";
    }

    @PostMapping("/guardar")
    public String guardarDetalleEntrega(@ModelAttribute DetalleEntregaRequestDto detalleEntrega, Model model) {
        try {
            servicioAPI.guardarDetalleEntrega(detalleEntrega);
            return "redirect:/detalleentrega";
        } catch (Exception ex) {
            model.addAttribute("detalleEntrega", detalleEntrega);
            model.addAttribute("error", MensajesError.extraer(ex));
            model.addAttribute("opcionesEntrega", servicioEntrega.listarOpciones());
            model.addAttribute("opcionesProducto", servicioProducto.listarOpciones());
            model.addAttribute("opcionesDetalleSolicitud", servicioDetalleSolicitud.listarOpciones());
            model.addAttribute("opcionesLote", servicioLote.listarOpciones());
            return "/DetalleEntrega/creardetalleentrega";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarDetalleEntrega(@PathVariable Integer id, Model model) {
        model.addAttribute("detalleEntrega", servicioAPI.buscarDetalleEntregaId(id));
        model.addAttribute("opcionesEntrega", servicioEntrega.listarOpciones());
        model.addAttribute("opcionesProducto", servicioProducto.listarOpciones());
        model.addAttribute("opcionesDetalleSolicitud", servicioDetalleSolicitud.listarOpciones());
        model.addAttribute("opcionesLote", servicioLote.listarOpciones());
        return "/DetalleEntrega/creardetalleentrega";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarDetalleEntrega(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            servicioAPI.eliminarDetalleEntrega(id);
        } catch (Exception ex) {
            // normalmente pasa cuando el registro esta usado por otro (clave foranea):
            // se avisa en pantalla en vez de mostrar la pagina de error de Spring.
            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/detalleentrega";
    }
}
