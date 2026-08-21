package com.translog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.translog.web.service.IConductorService;
import com.translog.web.service.IDespachoService;
import com.translog.web.service.IEnvioService;
import com.translog.web.service.IRutaService;
import com.translog.web.service.IVehiculoService;

@Controller
public class HomeController {

    @Autowired
    private IEnvioService servicioEnvio;
    @Autowired
    private IDespachoService servicioDespacho;
    @Autowired
    private IVehiculoService servicioVehiculo;
    @Autowired
    private IConductorService servicioConductor;
    @Autowired
    private IRutaService servicioRuta;

    @GetMapping("/")
    public String leerPagina(Model model) {
        var envios = servicioEnvio.listarEnvio();
        model.addAttribute("envios", envios.size());
        model.addAttribute("sinAsignar", envios.stream().filter(e -> e.getIdDespacho() == null).count());
        model.addAttribute("despachos", servicioDespacho.listarDespacho().size());
        model.addAttribute("rutas", servicioRuta.listarRuta().size());
        model.addAttribute("vehiculosDisponibles", servicioVehiculo.listarVehiculo().stream()
                .filter(v -> "DISPONIBLE".equals(v.getEstado())).count());
        model.addAttribute("conductoresHabilitados", servicioConductor.listarConductor().stream()
                .filter(c -> "HABILITADO".equals(c.getEstado())).count());
        return "/Home/home";
    }
}
