package com.bodega.controlweb.controller;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bodega.controlweb.model.dto.response.MovimientoReporteResponseDto;
import com.bodega.controlweb.service.IReporteService;
import com.bodega.controlweb.service.ISedeService;
import com.bodega.controlweb.service.ITipoService;

@Controller
@RequestMapping("/reporte")
public class ReporteController {

    @Autowired
    private IReporteService servicioAPI;
    @Autowired
    private ITipoService servicioTipo;
    @Autowired
    private ISedeService servicioSede;

    @GetMapping
    public String verReporte(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @RequestParam(required = false) Integer idTipo, @RequestParam(required = false) Integer idSede,
            Model model) {

        model.addAttribute("movimientos", servicioAPI.buscarMovimientos(desde, hasta, idTipo, idSede));
        model.addAttribute("opcionesTipo", servicioTipo.listarOpciones());
        model.addAttribute("opcionesSede", servicioSede.listarOpciones());
        model.addAttribute("desde", desde);
        model.addAttribute("hasta", hasta);
        model.addAttribute("idTipo", idTipo);
        model.addAttribute("idSede", idSede);
        return "/Reporte/reporte";
    }

    @GetMapping("/exportar")
    @ResponseBody
    public ResponseEntity<byte[]> exportarCsv(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @RequestParam(required = false) Integer idTipo, @RequestParam(required = false) Integer idSede) {

        List<MovimientoReporteResponseDto> movimientos = servicioAPI.buscarMovimientos(desde, hasta, idTipo, idSede);

        StringBuilder csv = new StringBuilder();
        csv.append("Fecha,Tipo,Producto,Lote,Ubicacion,Sede,Usuario\n");
        for (MovimientoReporteResponseDto mov : movimientos) {
            csv.append(csv(mov.getFechaRegistro())).append(',')
                    .append(csv(mov.getTipoMovimiento())).append(',')
                    .append(csv(mov.getNombreProducto())).append(',')
                    .append(csv(mov.getNumeroLote())).append(',')
                    .append(csv(mov.getCodigoUbicacion())).append(',')
                    .append(csv(mov.getNombreSede())).append(',')
                    .append(csv(mov.getNombreUsuario())).append('\n');
        }

        byte[] bytes = csv.toString().getBytes(StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename("reporte-movimientos.csv").build());
        headers.setContentType(MediaType.parseMediaType("text/csv; charset=UTF-8"));
        return ResponseEntity.ok().headers(headers).body(bytes);
    }

    private String csv(Object valor) {
        if (valor == null) {
            return "";
        }
        String texto = valor.toString().replace("\"", "\"\"");
        return "\"" + texto + "\"";
    }
}
