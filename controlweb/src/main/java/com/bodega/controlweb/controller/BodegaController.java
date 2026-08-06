package com.bodega.controlweb.controller;

import java.time.LocalDate;
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
 * Pantalla única de Bodegas. Para quien usa el sistema una bodega es una sola
 * cosa: un local con su nombre, su dirección, su capacidad y la zona en la que
 * está. Por debajo se siguen guardando sede, zona y ubicación, pero eso ya no
 * se le pide al usuario: la ubicación se crea sola con la bodega.
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
        List<SedeResponseDto> sedes = servicioSede.listarSede();
        Map<Integer, Integer> ocupado = servicioOcupacion.unidadesPorSede();

        model.addAttribute("sedes", sedes);
        model.addAttribute("zonaPorSede", zonaPorSede());
        model.addAttribute("ocupado", ocupado);
        model.addAttribute("reservado", servicioOcupacion.reservadasPorSede());
        model.addAttribute("hayExcedidas", sedes.stream().anyMatch(s -> s.getCapacidad() != null
                && s.getCapacidad() > 0 && ocupado.getOrDefault(s.getIdSede(), 0) > s.getCapacidad()));
        return "/Bodega/listarbodega";
    }

    @GetMapping("/nuevo")
    public String crear(Model model) {
        model.addAttribute("bodega", new BodegaRequestDto());
        return "/Bodega/crearbodega";
    }

    @GetMapping("/editar/{idSede}")
    public String editar(@PathVariable Integer idSede, Model model) {
        SedeResponseDto encontrada = servicioSede.buscarSedeId(idSede);
        BodegaRequestDto bodega = new BodegaRequestDto();
        bodega.setIdSede(encontrada.getIdSede());
        bodega.setNombreSede(encontrada.getNombreSede());
        bodega.setDireccion(encontrada.getDireccion());
        bodega.setDescripcion(encontrada.getDescripcion());
        bodega.setCapacidad(encontrada.getCapacidad());
        bodega.setNombreZona(zonaPorSede().get(idSede));

        model.addAttribute("bodega", bodega);
        return "/Bodega/crearbodega";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute BodegaRequestDto bodega, Model model, RedirectAttributes flash) {
        try {
            SedeRequestDto sede = new SedeRequestDto();
            sede.setIdSede(bodega.getIdSede());
            sede.setNombreSede(bodega.getNombreSede());
            sede.setDireccion(bodega.getDireccion());
            sede.setDescripcion(bodega.getDescripcion());
            sede.setCapacidad(bodega.getCapacidad());
            Integer idSede = servicioSede.guardarSede(sede).getIdSede();

            asegurarUbicacion(idSede, resolverZona(bodega.getNombreZona()));

            flash.addFlashAttribute("exito", "Bodega guardada correctamente.");
            return "redirect:/bodega";
        } catch (Exception ex) {
            model.addAttribute("bodega", bodega);
            model.addAttribute("error", MensajesError.extraer(ex));
            return "/Bodega/crearbodega";
        }
    }

    @GetMapping("/eliminar/{idSede}")
    public String eliminar(@PathVariable Integer idSede, RedirectAttributes flash) {
        try {
            // si todavía guarda mercadería, borrarla dejaría los lotes sin sitio
            int guardado = servicioOcupacion.unidadesPorSede().getOrDefault(idSede, 0);
            if (guardado > 0) {
                flash.addFlashAttribute("error", "No se puede eliminar esta bodega: todavía guarda "
                        + guardado + " unidades. Despacha o traslada la mercadería primero.");
                return "redirect:/bodega";
            }
            // primero las ubicaciones internas, que apuntan a la sede
            for (UbicacionResponseDto u : ubicacionesDe(idSede)) {
                servicioUbicacion.eliminarUbicacion(u.getIdUbicacion());
            }
            servicioSede.eliminarSede(idSede);
            flash.addFlashAttribute("exito", "Bodega eliminada.");
        } catch (Exception ex) {
            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/bodega";
    }

    /** Detalle rápido: qué productos guarda la bodega y en cuántos lotes vienen. */
    @GetMapping("/detalle/{idSede}")
    public String detalle(@PathVariable Integer idSede, Model model) {
        model.addAttribute("sede", servicioSede.buscarSedeId(idSede));
        model.addAttribute("zona", zonaPorSede().get(idSede));
        model.addAttribute("contenido", servicioOcupacion.contenidoPorSede(idSede));
        model.addAttribute("ocupado", servicioOcupacion.unidadesPorSede().getOrDefault(idSede, 0));
        model.addAttribute("reservado", servicioOcupacion.reservadasPorSede().getOrDefault(idSede, 0));
        model.addAttribute("lotes", servicioOcupacion.lotesPorSede().getOrDefault(idSede, 0));
        return "/Bodega/detallebodega";
    }

    /** Reutiliza la zona si ya existe una con ese nombre; si no, la crea. */
    private Integer resolverZona(String nombreZona) {
        if (nombreZona == null || nombreZona.isBlank()) {
            return null;
        }
        for (ZonaResponseDto z : servicioZona.listarZona()) {
            if (nombreZona.trim().replaceAll("\\s+", " ").equalsIgnoreCase(z.getNombreZona())) {
                return z.getIdZona();
            }
        }
        ZonaRequestDto zona = new ZonaRequestDto();
        zona.setNombreZona(nombreZona);
        zona.setDescripcion("Zona de la bodega");
        return servicioZona.guardarZona(zona).getIdZona();
    }

    /**
     * Cada bodega necesita una ubicación por dentro, que es a lo que se enganchan
     * los lotes. Se crea junto con la bodega y se actualiza su zona al editarla,
     * para que el usuario nunca tenga que saber que existe.
     */
    private void asegurarUbicacion(Integer idSede, Integer idZona) {
        List<UbicacionResponseDto> existentes = ubicacionesDe(idSede);
        UbicacionRequestDto ubicacion = new UbicacionRequestDto();
        ubicacion.setIdSede(idSede);
        ubicacion.setIdZona(idZona);
        ubicacion.setFechaUbicacion(LocalDate.now());

        if (existentes.isEmpty()) {
            ubicacion.setCodigoUbicacion("BOD-" + idSede);
        } else {
            UbicacionResponseDto actual = existentes.get(0);
            ubicacion.setIdUbicacion(actual.getIdUbicacion());
            ubicacion.setCodigoUbicacion(actual.getCodigoUbicacion());
            ubicacion.setFechaUbicacion(actual.getFechaUbicacion());
        }
        servicioUbicacion.guardarUbicacion(ubicacion);
    }

    private List<UbicacionResponseDto> ubicacionesDe(Integer idSede) {
        return servicioUbicacion.listarUbicacion().stream()
                .filter(u -> idSede.equals(u.getIdSede()))
                .toList();
    }

    /** Nombre de la zona en la que está cada bodega, sacado de su ubicación interna. */
    private Map<Integer, String> zonaPorSede() {
        Map<Integer, String> nombrePorZona = new HashMap<>();
        for (ZonaResponseDto z : servicioZona.listarZona()) {
            nombrePorZona.put(z.getIdZona(), z.getNombreZona());
        }

        Map<Integer, String> resultado = new HashMap<>();
        for (UbicacionResponseDto u : servicioUbicacion.listarUbicacion()) {
            if (u.getIdSede() != null && u.getIdZona() != null && !resultado.containsKey(u.getIdSede())) {
                resultado.put(u.getIdSede(), nombrePorZona.get(u.getIdZona()));
            }
        }
        return resultado;
    }
}
