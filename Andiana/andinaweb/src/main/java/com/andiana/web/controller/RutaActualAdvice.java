package com.andiana.web.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Deja la ruta que se está viendo al alcance de las plantillas, para que el menú
 * pueda marcar en qué paso del proceso está el usuario. Thymeleaf 3.1 ya no
 * expone la petición por su cuenta, así que se le pasa desde aquí.
 */
@ControllerAdvice
public class RutaActualAdvice {

	@ModelAttribute("rutaActual")
	public String rutaActual(HttpServletRequest peticion) {
		return peticion.getRequestURI();
	}
}
