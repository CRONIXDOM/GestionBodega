package com.bodega.controlweb.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bodega.controlweb.model.dto.request.BodegaRequestDto;
import com.bodega.controlweb.model.dto.request.SedeRequestDto;
import com.bodega.controlweb.model.dto.request.UbicacionRequestDto;
import com.bodega.controlweb.model.dto.request.ZonaRequestDto;
import com.bodega.controlweb.model.dto.response.SedeResponseDto;
import com.bodega.controlweb.model.dto.response.UbicacionResponseDto;
import com.bodega.controlweb.model.dto.response.ZonaResponseDto;
import com.bodega.controlweb.service.IOcupacionService;
import com.bodega.controlweb.service.ISedeService;
import com.bodega.controlweb.service.IUbicacionService;
import com.bodega.controlweb.service.IZonaService;
import com.bodega.controlweb.util.MensajesError;

/**
 * Pantalla única de Bodegas: reúne sedes, zonas y ubicaciones, que por separado
 * obligaban a recorrer tres secciones para dar de alta un solo sitio de
 * almacenamiento. Desde aquí se crea todo de una vez y se consulta de un
 * vistazo qué guarda cada bodega.
 */
@Controller
@RequestMapping("/bodega")
public class BodegaController {

    @Autowired
    private ISedeService servicioSede;
    @Autowired
    private IZonaService servicioZona;
    @Autowired
    private IUbicacionService servicioUbicacion;
    @Autowired
    private IOcupacionService servicioOcupacion;

    @GetMapping
    public String leerPagina(Model model) {
        cargarPanorama(model);
        return "/Bodega/listarbodega";
    }

    @GetMapping("/nuevo")
    public String crear(Model model) {
        model.addAttribute("bodega", new BodegaRequestDto());
        cargarOpciones(model);
        return "/Bodega/crearbodega";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute BodegaRequestDto bodega, Model model, RedirectAttributes flash) {
        try {
            Integer idSede = resolverSede(bodega);
            Integer idZona = resolverZona(bodega);

            UbicacionRequestDto ubicacion = new UbicacionRequestDto();
            ubicacion.setIdUbicacion(bodega.getIdUbicacion());
            ubicacion.setCodigoUbicacion(bodega.getCodigoUbicacion());
            ubicacion.setFechaUbicacion(bodega.getFechaUbicacion() == null ? LocalDate.now()
                    : bodega.getFechaUbicacion());
            ubicacion.setIdSede(idSede);
            ubicacion.setIdZona(idZona);
            servicioUbicacion.guardarUbicacion(ubicacion);

            flash.addFlashAttribute("exito", "Bodega guardada correctamente.");
            return "redirect:/bodega";
        } catch (Exception ex) {
            model.addAttribute("bodega", bodega);
            model.addAttribute("error", MensajesError.extraer(ex));
            cargarOpciones(model);
            return "/Bodega/crearbodega";
        }
    }

    /** Usa la sede elegida, o crea una nueva con los datos del formulario. */
    private Integer resolverSede(BodegaRequestDto bodega) {
        if (bodega.getIdSede() != null) {
            return bodega.getIdSede();
        }
        SedeRequestDto sede = new SedeRequestDto();
        sede.setNombreSede(bodega.getNombreSede());
        sede.setDireccion(bodega.getDireccion());
        sede.setDescripcion(bodega.getDescripcion());
        sede.setCapacidad(bodega.getCapacidad());
        return servicioSede.guardarSede(sede).getIdSede();
    }

    /** Usa la zona elegida, crea una nueva si se escribió un nombre, o ninguna. */
    private Integer resolverZona(BodegaRequestDto bodega) {
        if (bodega.getIdZona() != null) {
            return bodega.getIdZona();
        }
        if (bodega.getNombreZona() == null || bodega.getNombreZona().isBlank()) {
            return null;
        }
        // si ya existe una zona con ese nombre se reutiliza, para no duplicarla
        for (ZonaResponseDto z : servicioZona.listarZona()) {
            if (bodega.getNombreZona().trim().equalsIgnoreCase(z.getNombreZona())) {
                return z.getIdZona();
            }
        }
        ZonaRequestDto zona = new ZonaRequestDto();
        zona.setNombreZona(bodega.getNombreZona());
        zona.setDescripcion(bodega.getDescripcionZona());
        return servicioZona.guardarZona(zona).getIdZona();
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        UbicacionResponseDto ubicacion = servicioUbicacion.buscarUbicacionId(id);
        BodegaRequestDto bodega = new BodegaRequestDto();
        bodega.setIdUbicacion(ubicacion.getIdUbicacion());
        bodega.setCodigoUbicacion(ubicacion.getCodigoUbicacion());
        bodega.setFechaUbicacion(ubicacion.getFechaUbicacion());
        bodega.setIdSede(ubicacion.getIdSede());
        bodega.setIdZona(ubicacion.getIdZona());

        model.addAttribute("bodega", bodega);
        cargarOpciones(model);
        return "/Bodega/crearbodega";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            servicioUbicacion.eliminarUbicacion(id);
            flash.addFlashAttribute("exito", "Ubicación eliminada.");
        } catch (Exception ex) {
            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/bodega";
    }

    // ---------- la bodega en sí (la sede): crear, editar y eliminar ----------

    @GetMapping("/sede/nueva")
    public String crearSede(Model model) {
        model.addAttribute("sede", new SedeRequestDto());
        return "/Bodega/crearsede";
    }

    @GetMapping("/sede/editar/{id}")
    public String editarSede(@PathVariable Integer id, Model model) {
        SedeResponseDto encontrada = servicioSede.buscarSedeId(id);
        SedeRequestDto sede = new SedeRequestDto();
        sede.setIdSede(encontrada.getIdSede());
        sede.setNombreSede(encontrada.getNombreSede());
        sede.setDireccion(encontrada.getDireccion());
        sede.setDescripcion(encontrada.getDescripcion());
        sede.setCapacidad(encontrada.getCapacidad());

        model.addAttribute("sede", sede);
        return "/Bodega/crearsede";
    }

    @PostMapping("/sede/guardar")
    public String guardarSede(@ModelAttribute SedeRequestDto sede, Model model, RedirectAttributes flash) {
        try {
            servicioSede.guardarSede(sede);
            flash.addFlashAttribute("exito", "Bodega guardada correctamente.");
            return "redirect:/bodega";
        } catch (Exception ex) {
            model.addAttribute("sede", sede);
            model.addAttribute("error", MensajesError.extraer(ex));
            return "/Bodega/crearsede";
        }
    }

    @GetMapping("/sede/eliminar/{id}")
    public String eliminarSede(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            // borrar la sede dejaría sus ubicaciones (y la mercadería que guardan)
            // colgando sin bodega, así que se avisa en vez de romper el inventario.
            long ubicacionesDentro = servicioUbicacion.listarUbicacion().stream()
                    .filter(u -> id.equals(u.getIdSede())).count();
            if (ubicacionesDentro > 0) {
                flash.addFlashAttribute("error", "No se puede eliminar esta bodega: todavía tiene "
                        + ubicacionesDentro + " ubicación(es) dentro. Elimínalas primero.");
                return "redirect:/bodega";
            }
            servicioSede.eliminarSede(id);
            flash.addFlashAttribute("exito", "Bodega eliminada.");
        } catch (Exception ex) {
            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/bodega";
    }

    /** Detalle rápido: qué hay guardado en una sede concreta. */
    @GetMapping("/detalle/{idSede}")
    public String detalle(@PathVariable Integer idSede, Model model) {
        SedeResponseDto sede = servicioSede.buscarSedeId(idSede);
        List<UbicacionResponseDto> ubicaciones = servicioUbicacion.listarUbicacion().stream()
                .filter(u -> idSede.equals(u.getIdSede()))
                .sorted(Comparator.comparing(UbicacionResponseDto::getCodigoUbicacion,
                        Comparator.nullsLast(String::compareToIgnoreCase)))
                .toList();

        model.addAttribute("sede", sede);
        model.addAttribute("ubicaciones", ubicaciones);
        model.addAttribute("nombresZona", nombresZona());
        model.addAttribute("contenido", servicioOcupacion.contenidoPorUbicacion());
        model.addAttribute("unidades", servicioOcupacion.unidadesPorUbicacion());
        model.addAttribute("ocupado", servicioOcupacion.unidadesPorSede().getOrDefault(idSede, 0));
        model.addAttribute("reservado", servicioOcupacion.reservadasPorSede().getOrDefault(idSede, 0));
        return "/Bodega/detallebodega";
    }

    private void cargarOpciones(Model model) {
        model.addAttribute("opcionesSede", servicioSede.listarOpciones());
        model.addAttribute("opcionesZona", servicioZona.listarOpciones());
    }

    private Map<Integer, String> nombresZona() {
        Map<Integer, String> nombres = new HashMap<>();
        for (ZonaResponseDto z : servicioZona.listarZona()) {
            nombres.put(z.getIdZona(), z.getNombreZona());
        }
        return nombres;
    }

    private void cargarPanorama(Model model) {
        List<SedeResponseDto> sedes = servicioSede.listarSede();
        List<UbicacionResponseDto> ubicaciones = servicioUbicacion.listarUbicacion();
        Map<Integer, Integer> ocupado = servicioOcupacion.unidadesPorSede();
        Map<Integer, Integer> reservado = servicioOcupacion.reservadasPorSede();

        Map<Integer, List<UbicacionResponseDto>> porSede = new HashMap<>();
        for (UbicacionResponseDto u : ubicaciones) {
            porSede.computeIfAbsent(u.getIdSede(), k -> new ArrayList<>()).add(u);
        }

        model.addAttribute("sedes", sedes);
        model.addAttribute("ubicacionesPorSede", porSede);
        model.addAttribute("sinSede", porSede.getOrDefault(null, new ArrayList<>()));
        model.addAttribute("nombresZona", nombresZona());
        model.addAttribute("contenido", servicioOcupacion.contenidoPorUbicacion());
        model.addAttribute("unidades", servicioOcupacion.unidadesPorUbicacion());
        model.addAttribute("ocupado", ocupado);
        model.addAttribute("reservado", reservado);
        model.addAttribute("hayExcedidas", sedes.stream().anyMatch(s -> s.getCapacidad() != null
                && s.getCapacidad() > 0 && ocupado.getOrDefault(s.getIdSede(), 0) > s.getCapacidad()));
    }
}
