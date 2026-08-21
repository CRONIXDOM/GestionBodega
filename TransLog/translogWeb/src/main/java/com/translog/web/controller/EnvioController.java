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

import com.translog.web.model.dto.request.EnvioRequestDto;
import com.translog.web.service.IEnvioService;
import com.translog.web.service.ICiudadService;
import com.translog.web.util.MensajesError;

@Controller
@RequestMapping("/envio")
public class EnvioController {

    @Autowired
    private IEnvioService servicioAPI;
    @Autowired
    private ICiudadService servicioCiudad;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("ciudades", nombresDeCiudad());
        model.addAttribute("listaenvio", servicioAPI.listarEnvio());
        return "/Envio/listarenvio";
    }

    @GetMapping("/nuevo")
    public String crearEnvio(Model model) {
        model.addAttribute("envio", new EnvioRequestDto());
        agregarOpciones(model);
        return "/Envio/crearenvio";
    }

    @PostMapping("/guardar")
    public String guardarEnvio(@ModelAttribute EnvioRequestDto envio, Model model) {
        try {
            servicioAPI.guardarEnvio(envio);
            return "redirect:/envio";
        } catch (Exception ex) {
            // se vuelve al formulario con lo ya escrito y el motivo del rechazo
            model.addAttribute("envio", envio);
            model.addAttribute("error", MensajesError.extraer(ex));
            agregarOpciones(model);
            return "/Envio/crearenvio";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarEnvio(@PathVariable Integer id, Model model) {
        model.addAttribute("envio", servicioAPI.buscarEnvioId(id));
        agregarOpciones(model);
        return "/Envio/crearenvio";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEnvio(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            servicioAPI.eliminarEnvio(id);
        } catch (Exception ex) {
            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/envio";
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
