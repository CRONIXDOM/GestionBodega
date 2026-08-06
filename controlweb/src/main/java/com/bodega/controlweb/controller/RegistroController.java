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

import com.bodega.controlweb.model.dto.request.RegistroRequestDto;
import com.bodega.controlweb.service.IDetalleEntregaService;
import com.bodega.controlweb.service.IEtiquetasService;
import com.bodega.controlweb.service.ILoteService;
import com.bodega.controlweb.service.IRegistroService;
import com.bodega.controlweb.service.ITipoService;
import com.bodega.controlweb.service.IUbicacionService;
import com.bodega.controlweb.service.IUsuarioRolService;
import com.bodega.controlweb.util.MensajesError;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private IRegistroService servicioAPI;
    @Autowired
    private IEtiquetasService servicioEtiquetas;
    @Autowired
    private ILoteService servicioLote;
    @Autowired
    private ITipoService servicioTipo;
    @Autowired
    private IUbicacionService servicioUbicacion;
    @Autowired
    private IDetalleEntregaService servicioDetalleEntrega;
    @Autowired
    private IUsuarioRolService servicioUsuarioRol;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("bodegas", servicioEtiquetas.bodegaPorUbicacion());
        model.addAttribute("listaregistro", servicioAPI.listarRegistro());
        return "/Registro/listarregistro";
    }

    @GetMapping("/nuevo")
    public String crearRegistro(Model model) {
        model.addAttribute("registro", new RegistroRequestDto());
        agregarOpciones(model);
        return "/Registro/crearregistro";
    }

    @PostMapping("/guardar")
    public String guardarRegistro(@ModelAttribute RegistroRequestDto registro, Model model) {
        try {
            servicioAPI.guardarRegistro(registro);
            return "redirect:/registro";
        } catch (Exception ex) {
            model.addAttribute("registro", registro);
            model.addAttribute("error", MensajesError.extraer(ex));
            agregarOpciones(model);
            return "/Registro/crearregistro";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarRegistro(@PathVariable Integer id, Model model) {
        model.addAttribute("registro", servicioAPI.buscarRegistroId(id));
        agregarOpciones(model);
        return "/Registro/crearregistro";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarRegistro(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            servicioAPI.eliminarRegistro(id);
        } catch (Exception ex) {
            // normalmente pasa cuando el registro esta usado por otro (clave foranea):
            // se avisa en pantalla en vez de mostrar la pagina de error de Spring.
            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/registro";
    }

    private void agregarOpciones(Model model) {
        model.addAttribute("opcionesLote", servicioLote.listarOpciones());
        model.addAttribute("opcionesTipo", servicioTipo.listarOpciones());
        model.addAttribute("opcionesUbicacion", servicioUbicacion.listarOpciones());
        model.addAttribute("opcionesDetalleEntrega", servicioDetalleEntrega.listarOpciones());
        model.addAttribute("opcionesUsuarioRol", servicioUsuarioRol.listarOpciones());
    }
}
