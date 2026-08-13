package com.andiana.web.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RutaActualAdvice {

	@ModelAttribute("rutaActual")
	public String rutaActual(HttpServletRequest peticion) {
		return peticion.getRequestURI();
	}
}
