package com.bodega.controlweb.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

import com.bodega.controlweb.model.dto.request.DetalleEntregaRequestDto;
import com.bodega.controlweb.model.dto.request.EntregaRequestDto;
import com.bodega.controlweb.model.dto.request.RegistroRequestDto;
import com.bodega.controlweb.model.dto.request.TipoRequestDto;
import com.bodega.controlweb.model.dto.response.DetalleEntregaResponseDto;
import com.bodega.controlweb.model.dto.response.DetalleSolicitudResponseDto;
import com.bodega.controlweb.model.dto.response.EntregaResponseDto;
import com.bodega.controlweb.model.dto.response.LoteAsignadoResponseDto;
import com.bodega.controlweb.model.dto.response.LoteResponseDto;
import com.bodega.controlweb.model.dto.response.TipoResponseDto;
import com.bodega.controlweb.model.dto.response.UsuarioRolResponseDto;
import com.bodega.controlweb.service.IDetalleEntregaService;
import com.bodega.controlweb.service.IEtiquetasService;
import com.bodega.controlweb.service.IDetalleSolicitudService;
import com.bodega.controlweb.service.IEntregaService;
import com.bodega.controlweb.service.ILoteService;
import com.bodega.controlweb.service.IRegistroService;
import com.bodega.controlweb.service.ISolicitudService;
import com.bodega.controlweb.service.ITipoService;
import com.bodega.controlweb.service.IUsuarioRolService;
import com.bodega.controlweb.util.MensajesError;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/entrega")
public class EntregaController {

    private static final String DESCRIPCION_TIPO_SALIDA = "Salida por entrega";

    @Autowired
    private IEntregaService servicioAPI;
    @Autowired
    private IEtiquetasService servicioEtiquetas;
    @Autowired
    private ISolicitudService servicioSolicitud;
    @Autowired
    private IDetalleSolicitudService servicioDetalleSolicitud;
    @Autowired
    private IDetalleEntregaService servicioDetalleEntrega;
    @Autowired
    private IRegistroService servicioRegistro;
    @Autowired
    private ILoteService servicioLote;
    @Autowired
    private ITipoService servicioTipo;
    @Autowired
    private IUsuarioRolService servicioUsuarioRol;

    @GetMapping
    public String leerPagina(Model model) {
        model.addAttribute("solicitudPorEntrega", servicioEtiquetas.solicitudPorEntrega());
        model.addAttribute("solicitantePorSolicitud", servicioEtiquetas.solicitantePorSolicitud());
        model.addAttribute("listaentrega", servicioAPI.listarEntrega());
        return "/Entrega/listarentrega";
    }

    @GetMapping("/nuevo")
    public String crearEntrega(Model model) {
        model.addAttribute("entrega", new EntregaRequestDto());
        model.addAttribute("opcionesSolicitud", servicioSolicitud.listarOpciones());
        return "/Entrega/crearentrega";
    }

    @PostMapping("/guardar")
    public String guardarEntrega(@ModelAttribute EntregaRequestDto entrega,
            @RequestParam(required = false) Integer idSolicitud, Model model, HttpSession session) {
        try {
            EntregaResponseDto guardada = servicioAPI.guardarEntrega(entrega);
            if (idSolicitud != null) {
                despacharSolicitud(guardada.getIdEntrega(), idSolicitud, session);
            }
            return "redirect:/entrega";
        } catch (Exception ex) {
            model.addAttribute("entrega", entrega);
            model.addAttribute("error", MensajesError.extraer(ex));
            model.addAttribute("opcionesSolicitud", servicioSolicitud.listarOpciones());
            return "/Entrega/crearentrega";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarEntrega(@PathVariable Integer id, Model model) {
        model.addAttribute("entrega", servicioAPI.buscarEntregaId(id));
        model.addAttribute("opcionesSolicitud", servicioSolicitud.listarOpciones());
        return "/Entrega/crearentrega";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEntrega(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            servicioAPI.eliminarEntrega(id);
        } catch (Exception ex) {
            // normalmente pasa cuando el registro esta usado por otro (clave foranea):
            // se avisa en pantalla en vez de mostrar la pagina de error de Spring.
            flash.addFlashAttribute("error", MensajesError.alEliminar(ex));
        }
        return "redirect:/entrega";
    }

    /**
     * Despacha cada línea de la solicitud elegida (respetando lo ya reservado
     * en sus lotes vía IDetalleEntregaUseCase.despacharDesdeReserva) y deja un
     * Registro por cada lote afectado, para que quede el resumen del movimiento.
     */
    private void despacharSolicitud(Integer idEntrega, Integer idSolicitud, HttpSession session) {
        List<DetalleSolicitudResponseDto> lineas = servicioDetalleSolicitud.listarDetalleSolicitud().stream()
                .filter(d -> idSolicitud.equals(d.getIdSolicitud())).toList();

        Integer idTipoSalida = resolverTipoSalida();
        Integer idUsuarioRol = resolverUsuarioRolSesion(session);

        for (DetalleSolicitudResponseDto linea : lineas) {
            DetalleEntregaRequestDto detalle = new DetalleEntregaRequestDto();
            detalle.setIdEntrega(idEntrega);
            detalle.setIdDetalleSolicitud(linea.getIdDetalleSolicitud());
            DetalleEntregaResponseDto detalleGuardado = servicioDetalleEntrega.guardarDetalleEntrega(detalle);

            if (linea.getLotesAsignados() != null) {
                for (LoteAsignadoResponseDto asignado : linea.getLotesAsignados()) {
                    RegistroRequestDto registro = new RegistroRequestDto();
                    registro.setFechaRegistro(LocalDate.now());
                    registro.setIdLote(asignado.getIdLote());
                    registro.setIdTipo(idTipoSalida);
                    registro.setIdUbicacion(resolverUbicacionDeLote(asignado.getIdLote()));
                    registro.setIdDetalleEntrega(detalleGuardado.getIdDetalleEntrega());
                    registro.setIdUsuarioRol(idUsuarioRol);
                    servicioRegistro.guardarRegistro(registro);
                }
            }
        }
    }

    private Integer resolverUbicacionDeLote(Integer idLote) {
        if (idLote == null) {
            return null;
        }
        LoteResponseDto lote = servicioLote.buscarLoteId(idLote);
        return lote == null ? null : lote.getIdUbicacion();
    }

    private Integer resolverUsuarioRolSesion(HttpSession session) {
        Object idUsuario = session.getAttribute("idUsuarioLogueado");
        if (idUsuario == null) {
            return null;
        }
        return servicioUsuarioRol.listarUsuarioRol().stream()
                .filter(a -> idUsuario.equals(a.getIdUsuario()))
                .map(UsuarioRolResponseDto::getIdUsuarioRol)
                .findFirst().orElse(null);
    }

    private Integer resolverTipoSalida() {
        Optional<TipoResponseDto> existente = servicioTipo.listarTipo().stream()
                .filter(t -> DESCRIPCION_TIPO_SALIDA.equalsIgnoreCase(t.getDescripcion())).findFirst();
        if (existente.isPresent()) {
            return existente.get().getIdTipo();
        }
        TipoRequestDto nuevo = new TipoRequestDto();
        nuevo.setDescripcion(DESCRIPCION_TIPO_SALIDA);
        nuevo.setClase("Salida");
        servicioTipo.guardarTipo(nuevo);
        return servicioTipo.listarTipo().stream()
                .filter(t -> DESCRIPCION_TIPO_SALIDA.equalsIgnoreCase(t.getDescripcion())).findFirst()
                .map(TipoResponseDto::getIdTipo).orElse(null);
    }
}
