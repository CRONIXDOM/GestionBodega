package com.translog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.translog.web.model.dto.request.RutaRequestDto;
import com.translog.web.service.IRutaService;
import com.translog.web.service.ICiudadService;
import com.translog.web.util.MensajesError;

@Controller
@RequestMapping("/ruta")
public class RutaController {

    @Autowired
    private IRutaService servicioAPI;
    @Autowired
    private ICiudadService servicioCiudad;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("ciudades", nombresDeCiudad());
        model.addAttribute("listaruta", servicioAPI.listarRuta());
        return "/Ruta/listarruta";
    }

    @GetMapping("/nuevo")
    public String crearRuta(Model model) {
        model.addAttribute("ruta", new RutaRequestDto());
        agregarOpciones(model);
        return "/Ruta/crearruta";
    }

    @PostMapping("/guardar")
    public String guardarRuta(@ModelAttribute RutaRequestDto ruta, Model model) {
        try {
            servicioAPI.guardarRuta(ruta);
            return "redirect:/ruta";
        } catch (Exception ex) {
            // se vuelve al formulario con lo ya escrito y el motivo del rechazo
            model.addAttribute("ruta", ruta);
            model.addAttribute("error", MensajesError.extraer(ex));
            agregarOpciones(model);
            return "/Ruta/crearruta";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarRuta(@PathVariable Integer id, Model model) {
        model.addAttribute("ruta", servicioAPI.buscarRutaId(id));
        agregarOpciones(model);
        return "/Ruta/crearruta";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarRuta(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            servicioAPI.eliminarRuta(id);
        } catch (Exception ex) {
            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/ruta";
    }

    /** El nombre de cada ciudad por su id, para que la tabla no muestre números. */
    private java.util.Map<Integer, String> nombresDeCiudad() {
        java.util.Map<Integer, String> nombres = new java.util.HashMap<>();
        servicioCiudad.listarCiudad().forEach(c -> nombres.put(c.getIdCiudad(), c.getNombre()));
        return nombres;
    }

    private void agregarOpciones(Model model) {
        model.addAttribute("opcionesCiudad", servicioCiudad.listarOpciones());
    }
}
