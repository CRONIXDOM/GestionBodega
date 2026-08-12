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

import com.bodega.controlweb.model.dto.request.ProductoRequestDto;
import com.bodega.controlweb.service.IProductoService;
import com.bodega.controlweb.util.MensajesError;

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
    public String guardarProducto(@ModelAttribute ProductoRequestDto producto, Model model) {
        try {
            servicioAPI.guardarProducto(producto);
            return "redirect:/producto";
        } catch (Exception ex) {
            // se vuelve al formulario con lo ya escrito y el motivo del rechazo,
            // en vez de mostrar la pagina de error de Spring.
            model.addAttribute("producto", producto);
            model.addAttribute("error", MensajesError.extraer(ex));
            return "/Producto/crearproducto";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Integer id, Model model) {
        model.addAttribute("producto", servicioAPI.buscarProductoId(id));
        return "/Producto/crearproducto";
    }

    /**
     * Eliminar da de baja el producto: sale del listado y de los selectores, pero
     * su historial queda intacto y se puede recuperar desde "Eliminados".
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            servicioAPI.eliminarProducto(id);
            flash.addFlashAttribute("exito", "Producto eliminado. Puedes recuperarlo desde «Eliminados».");
        } catch (Exception ex) {
            // normalmente pasa cuando todavia queda mercaderia: se avisa en pantalla
            // en vez de mostrar la pagina de error de Spring.
            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/producto";
    }

    @GetMapping("/eliminados")
    public String productosEliminados(Model model) {
        model.addAttribute("listaproducto", servicioAPI.listarEliminados());
        return "/Producto/eliminados";
    }

    @GetMapping("/recuperar/{id}")
    public String recuperarProducto(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            servicioAPI.recuperarProducto(id);
            flash.addFlashAttribute("exito", "Producto recuperado: vuelve a estar disponible.");
        } catch (Exception ex) {
            flash.addFlashAttribute("error", MensajesError.extraer(ex));
        }
        return "redirect:/producto/eliminados";
    }
}
