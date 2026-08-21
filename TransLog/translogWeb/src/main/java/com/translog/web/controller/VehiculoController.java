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

import com.translog.web.model.dto.request.VehiculoRequestDto;
import com.translog.web.service.IVehiculoService;
import com.translog.web.util.MensajesError;

@Controller
@RequestMapping("/vehiculo")
public class VehiculoController {

    @Autowired
    private IVehiculoService servicioAPI;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("listavehiculo", servicioAPI.listarVehiculo());
        return "/Vehiculo/listarvehiculo";
    }

    @GetMapping("/nuevo")
    public String crearVehiculo(Model model) {
        model.addAttribute("vehiculo", new VehiculoRequestDto());
        agregarOpciones(model);
        return "/Vehiculo/crearvehiculo";
    }

    @PostMapping("/guardar")
    public String guardarVehiculo(@ModelAttribute VehiculoRequestDto vehiculo, Model model) {
        try {
            servicioAPI.guardarVehiculo(vehiculo);
            return "redirect:/vehiculo";
        } catch (Exception ex) {
            // se vuelve al formulario con lo ya escrito y el motivo del rechazo
            model.addAttribute("vehiculo", vehiculo);
            model.addAttribute("error", MensajesError.extraer(ex));
            agregarOpciones(model);
            return "/Vehiculo/crearvehiculo";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarVehiculo(@PathVariable Integer id, Model model) {
        model.addAttribute("vehiculo", servicioAPI.buscarVehiculoId(id));
        agregarOpciones(model);
        return "/Vehiculo/crearvehiculo";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarVehiculo(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            servicioAPI.eliminarVehiculo(id);
        } catch (Exception ex) {
            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/vehiculo";
    }

    private void agregarOpciones(Model model) {
    }
}
