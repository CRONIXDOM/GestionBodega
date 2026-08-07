package com.andiana.web.controlador;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.andiana.web.cliente.ApiCliente;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Red de seguridad: cualquier fallo que se escape termina como un aviso en la
 * pantalla anterior, nunca como la pagina blanca de error.
 */
@ControllerAdvice
public class ManejadorErrores {

	private final ApiCliente api;

	public ManejadorErrores(ApiCliente api) {
		this.api = api;
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public String seccionInexistente(RedirectAttributes flash) {
		flash.addFlashAttribute("error", "La pantalla solicitada no existe.");
		return "redirect:/";
	}

	@ExceptionHandler(Exception.class)
	public String fallo(Exception ex, HttpServletRequest peticion, RedirectAttributes flash) {
		flash.addFlashAttribute("error", api.mensajeDe(ex));
		String origen = peticion.getHeader("Referer");
		boolean vuelveASiMismo = origen != null && origen.endsWith(peticion.getRequestURI());
		return (origen == null || origen.isBlank() || vuelveASiMismo) ? "redirect:/" : "redirect:" + origen;
	}
}
