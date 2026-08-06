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
import org.springframework.web.bind.annotation.RequestParam;

import com.bodega.controlweb.model.dto.request.DetalleSolicitudRequestDto;
import com.bodega.controlweb.model.dto.response.DetalleSolicitudResponseDto;
import com.bodega.controlweb.service.IDetalleSolicitudService;
import com.bodega.controlweb.util.MensajesError;
import com.bodega.controlweb.service.IEtiquetasService;
import com.bodega.controlweb.service.ILoteService;
import com.bodega.controlweb.service.IProductoService;
import com.bodega.controlweb.service.ISolicitudService;

@Controller
@RequestMapping("/detallesolicitud")
public class DetalleSolicitudController {

    @Autowired
    private IDetalleSolicitudService servicioAPI;
    @Autowired
    private IEtiquetasService servicioEtiquetas;
    @Autowired
    private IProductoService servicioProducto;
    @Autowired
    private ISolicitudService servicioSolicitud;
    @Autowired
    private ILoteService servicioLote;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("productos", servicioEtiquetas.productosPorId());
        model.addAttribute("listadetallesolicitud", servicioAPI.listarDetalleSolicitud());
        return "/DetalleSolicitud/listardetallesolicitud";
    }

    @GetMapping("/nuevo")
    public String crearDetalleSolicitud(@RequestParam(required = false) Integer idLote,
            @RequestParam(required = false) Integer idProducto, Model model) {
        DetalleSolicitudRequestDto nuevo = new DetalleSolicitudRequestDto();
        nuevo.setIdLote(idLote);
        nuevo.setIdProducto(idProducto);
        model.addAttribute("detalleSolicitud", nuevo);
        model.addAttribute("opcionesProducto", servicioProducto.listarOpciones());
        model.addAttribute("opcionesSolicitud", servicioSolicitud.listarOpciones());
        model.addAttribute("opcionesLote", servicioLote.listarOpciones());
        return "/DetalleSolicitud/creardetallesolicitud";
    }

    @PostMapping("/guardar")
    public String guardarDetalleSolicitud(@ModelAttribute DetalleSolicitudRequestDto detalleSolicitud, Model model) {
        try {
            servicioAPI.guardarDetalleSolicitud(detalleSolicitud);
            return "redirect:/detallesolicitud";
        } catch (Exception ex) {
            // aqui cae, sobre todo, el "stock insuficiente": interesa que el usuario
            // lo lea en el mismo formulario y corrija la cantidad.
            model.addAttribute("detalleSolicitud", detalleSolicitud);
            model.addAttribute("error", MensajesError.extraer(ex));
            model.addAttribute("opcionesProducto", servicioProducto.listarOpciones());
            model.addAttribute("opcionesSolicitud", servicioSolicitud.listarOpciones());
            model.addAttribute("opcionesLote", servicioLote.listarOpciones());
            return "/DetalleSolicitud/creardetallesolicitud";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarDetalleSolicitud(@PathVariable Integer id, Model model) {
        // el formulario trabaja sobre el DTO de peticion (que si tiene idLote); pasarle
        // el de respuesta hacia fallar la pantalla al pintar el selector de lote.
        DetalleSolicitudResponseDto encontrado = servicioAPI.buscarDetalleSolicitudId(id);
        DetalleSolicitudRequestDto detalleSolicitud = new DetalleSolicitudRequestDto();
        detalleSolicitud.setIdDetalleSolicitud(encontrado.getIdDetalleSolicitud());
        detalleSolicitud.setCantidadProducto(encontrado.getCantidadProducto());
        detalleSolicitud.setLugarRecogida(encontrado.getLugarRecogida());
        detalleSolicitud.setIdProducto(encontrado.getIdProducto());
        detalleSolicitud.setIdSolicitud(encontrado.getIdSolicitud());

        model.addAttribute("detalleSolicitud", detalleSolicitud);
        model.addAttribute("opcionesProducto", servicioProducto.listarOpciones());
        model.addAttribute("opcionesSolicitud", servicioSolicitud.listarOpciones());
        model.addAttribute("opcionesLote", servicioLote.listarOpciones());
        return "/DetalleSolicitud/creardetallesolicitud";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarDetalleSolicitud(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            servicioAPI.eliminarDetalleSolicitud(id);
        } catch (Exception ex) {
            // normalmente pasa cuando el registro esta usado por otro (clave foranea):
            // se avisa en pantalla en vez de mostrar la pagina de error de Spring.
            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/detallesolicitud";
    }
}
