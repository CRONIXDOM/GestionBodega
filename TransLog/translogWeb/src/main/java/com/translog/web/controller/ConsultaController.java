package com.translog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.translog.web.service.IConsultaService;

@Controller
@RequestMapping("/consulta")
public class ConsultaController {

    @Autowired
    private IConsultaService servicioAPI;

    /** Los despachos con mayor cantidad de envíos asociados. */
    @GetMapping("/despachos-con-mas-envios")
    public String despachosConMasEnvios(Model model) {
        model.addAttribute("ranking", servicioAPI.despachosConMasEnvios());
        return "/Consulta/despachosconmasenvios";
    }
}
