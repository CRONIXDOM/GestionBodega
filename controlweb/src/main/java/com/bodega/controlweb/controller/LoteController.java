package com.bodega.controlweb.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bodega.controlweb.model.dto.request.LoteRequestDto;
import com.bodega.controlweb.model.dto.response.LoteResponseDto;
import com.bodega.controlweb.model.dto.response.ProductoResponseDto;
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
        // los lotes de un producto dado de baja no se listan: el producto ya no
        // esta en el catalogo, y esos lotes solo siguen existiendo porque el
        // historial de movimientos los nombra
        Set<Integer> productosActivos = servicioProducto.listarProducto().stream()
                .map(ProductoResponseDto::getIdProducto)
                .collect(Collectors.toSet());

        model.addAttribute("productos", servicioEtiquetas.productosPorId());
        model.addAttribute("bodegas", servicioEtiquetas.bodegaPorUbicacion());
        model.addAttribute("listalote", servicioAPI.listarLote().stream()
                .filter(lote -> productosActivos.contains(lote.getIdProducto()))
                .toList());
        return "/Lote/listarlote";
    }

    @GetMapping("/nuevo")
    public String crearLote(Model model) {
        model.addAttribute("lote", new LoteRequestDto());
        agregarOpciones(model);
        repartirEnCajas(model, null, null);
        return "/Lote/crearlote";
    }

    @PostMapping("/guardar")
    public String guardarLote(@ModelAttribute LoteRequestDto lote,
            @RequestParam(required = false) Integer cajas,
            @RequestParam(required = false) Integer unidadesSueltas, Model model) {

        lote.setCantidadLote(enUnidades(lote.getIdProducto(), cajas, unidadesSueltas));
        try {
            servicioAPI.guardarLote(lote);
            return "redirect:/lote";
        } catch (Exception ex) {
            // se vuelve al formulario con lo ya escrito y el motivo del rechazo,
            // en vez de mostrar la pagina de error de Spring.
            model.addAttribute("lote", lote);
            model.addAttribute("error", MensajesError.extraer(ex));
            model.addAttribute("cajas", cajas);
            model.addAttribute("unidadesSueltas", unidadesSueltas);
            agregarOpciones(model);
            return "/Lote/crearlote";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarLote(@PathVariable Integer id, Model model) {
        LoteResponseDto lote = servicioAPI.buscarLoteId(id);
        model.addAttribute("lote", lote);
        agregarOpciones(model);
        repartirEnCajas(model, lote.getIdProducto(), lote.getCantidadLote());
        return "/Lote/crearlote";
    }

    private void agregarOpciones(Model model) {
        model.addAttribute("opcionesProducto", servicioProducto.listarOpciones());
        model.addAttribute("opcionesUbicacion", servicioUbicacion.listarOpciones());
        model.addAttribute("unidadesPorCaja", unidadesPorCajaPorProducto());
    }

    /**
     * El lote se carga en cajas, que es como llega la mercaderia, pero lo que se
     * guarda son unidades: es la medida con la que trabajan las reservas, las
     * entregas y la capacidad de la bodega. Aqui se pasa de una a otra.
     *
     * El calculo se rehace en el servidor y no se confia en lo que llegue del
     * formulario, para que el total no dependa de que el navegador ejecute el
     * JavaScript.
     */
    private Integer enUnidades(Integer idProducto, Integer cajas, Integer unidadesSueltas) {
        int porCaja = unidadesPorCajaPorProducto().getOrDefault(idProducto, 1);
        int enCajas = cajas == null || cajas < 0 ? 0 : cajas;
        int sueltas = unidadesSueltas == null || unidadesSueltas < 0 ? 0 : unidadesSueltas;
        return enCajas * porCaja + sueltas;
    }

    /**
     * El camino de vuelta, para editar: de las unidades guardadas a cuantas cajas
     * son. Las que sobran se muestran aparte, porque una entrega puede haberse
     * llevado media caja y el resto ya no es un numero redondo.
     */
    private void repartirEnCajas(Model model, Integer idProducto, Integer cantidadLote) {
        int porCaja = unidadesPorCajaPorProducto().getOrDefault(idProducto, 0);
        int total = cantidadLote == null ? 0 : cantidadLote;
        if (porCaja <= 0) {
            model.addAttribute("cajas", null);
            model.addAttribute("unidadesSueltas", total > 0 ? total : null);
            return;
        }
        model.addAttribute("cajas", total / porCaja);
        model.addAttribute("unidadesSueltas", total % porCaja);
    }

    private Map<Integer, Integer> unidadesPorCajaPorProducto() {
        Map<Integer, Integer> porProducto = new LinkedHashMap<>();
        for (ProductoResponseDto producto : servicioProducto.listarProducto()) {
            porProducto.put(producto.getIdProducto(),
                    producto.getUnidadesPorCaja() == null ? 1 : producto.getUnidadesPorCaja());
        }
        return porProducto;
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
