package com.translog.web.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Thymeleaf 3.1 ya no expone la peticion en las plantillas, y el menu necesita
 * saber en que pantalla esta para marcar la opcion activa.
 */
@ControllerAdvice
public class RutaActualAdvice {

    @ModelAttribute("rutaActual")
    public String rutaActual(HttpServletRequest peticion) {
        return peticion.getRequestURI();
    }
}
