package com.bodega.controlweb.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bodega.controlweb.model.dto.request.UbicacionRequestDto;
import com.bodega.controlweb.model.dto.response.SedeResponseDto;
import com.bodega.controlweb.model.dto.response.UbicacionResponseDto;
import com.bodega.controlweb.model.dto.response.ZonaResponseDto;
import com.bodega.controlweb.service.IOcupacionService;
import com.bodega.controlweb.service.ISedeService;
import com.bodega.controlweb.service.IUbicacionService;
import com.bodega.controlweb.service.IZonaService;

@Controller
@RequestMapping("/ubicacion")
public class UbicacionController {

    @Autowired
    private IUbicacionService servicioAPI;
    @Autowired
    private IZonaService servicioZona;
    @Autowired
    private ISedeService servicioSede;
    @Autowired
    private IOcupacionService servicioOcupacion;

    @GetMapping
    public String leerPagina(Model model) {
        List<UbicacionResponseDto> ubicaciones = servicioAPI.listarUbicacion();
        Map<Integer, String> nombresZona = servicioZona.listarZona().stream()
                .collect(Collectors.toMap(ZonaResponseDto::getIdZona, ZonaResponseDto::getNombreZona));
        Map<Integer, String> nombresSede = servicioSede.listarSede().stream()
                .collect(Collectors.toMap(SedeResponseDto::getIdSede, SedeResponseDto::getNombreSede));

        model.addAttribute("listaubicacion", ubicaciones);
        model.addAttribute("nombresZona", nombresZona);
        model.addAttribute("nombresSede", nombresSede);
        // qué productos hay guardados en cada ubicación y cuántas unidades de cada uno
        model.addAttribute("contenido", servicioOcupacion.contenidoPorUbicacion());
        model.addAttribute("unidades", servicioOcupacion.unidadesPorUbicacion());
        return "/Ubicacion/listarubicacion";
    }

    @GetMapping("/nuevo")
    public String crearUbicacion(Model model) {
        model.addAttribute("ubicacion", new UbicacionRequestDto());
        model.addAttribute("opcionesZona", servicioZona.listarOpciones());
        model.addAttribute("opcionesSede", servicioSede.listarOpciones());
        return "/Ubicacion/crearubicacion";
    }

    @PostMapping("/guardar")
    public String guardarUbicacion(@ModelAttribute UbicacionRequestDto ubicacion) {
        servicioAPI.guardarUbicacion(ubicacion);
        return "redirect:/ubicacion";
    }

    @GetMapping("/editar/{id}")
    public String editarUbicacion(@PathVariable Integer id, Model model) {
        model.addAttribute("ubicacion", servicioAPI.buscarUbicacionId(id));
        model.addAttribute("opcionesZona", servicioZona.listarOpciones());
        model.addAttribute("opcionesSede", servicioSede.listarOpciones());
        return "/Ubicacion/crearubicacion";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUbicacion(@PathVariable Integer id) {
        servicioAPI.eliminarUbicacion(id);
        return "redirect:/ubicacion";
    }
}
