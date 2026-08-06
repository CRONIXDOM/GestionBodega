package com.bodega.controlweb.controller;

import java.util.List;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bodega.controlweb.util.MensajesError;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Red de seguridad para toda la aplicación. Antes, si un dato no se podía
 * convertir (por ejemplo escribir "1.5" donde se esperan unidades enteras),
 * Spring cortaba la petición ANTES de llegar al controlador y el usuario veía
 * la página blanca de error. Aquí se atrapa lo que se escape y se devuelve al
 * usuario a la pantalla en la que estaba, con un aviso entendible.
 */
@ControllerAdvice
public class ManejadorErroresGlobal {

	/** Datos que no encajan con el tipo del campo: números con coma, letras, etc. */
	@ExceptionHandler({ MethodArgumentNotValidException.class, BindException.class })
	public String datoConFormatoInvalido(BindException ex, HttpServletRequest peticion, RedirectAttributes flash) {
		flash.addFlashAttribute("error", describir(ex.getFieldErrors()));
		return volverAtras(peticion);
	}

	/** Un id que no es un número, típicamente al manipular la URL a mano. */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public String idInvalido(HttpServletRequest peticion, RedirectAttributes flash) {
		flash.addFlashAttribute("error", "La dirección solicitada no es válida.");
		return volverAtras(peticion);
	}

	/**
	 * Cualquier otro fallo no previsto: mejor un aviso que una página en blanco.
	 * Los "no encontrado" se dejan pasar para que sigan respondiendo 404 y no se
	 * conviertan en un redirect que dé vueltas.
	 */
	@ExceptionHandler(Exception.class)
	public String falloInesperado(Exception ex, HttpServletRequest peticion, RedirectAttributes flash)
			throws Exception {
		if (ex instanceof NoResourceFoundException || ex instanceof ResponseStatusException) {
			throw ex;
		}
		flash.addFlashAttribute("error", MensajesError.extraer(ex));
		return volverAtras(peticion);
	}

	private String describir(List<FieldError> errores) {
		if (errores.isEmpty()) {
			return "Revisa los datos del formulario: alguno no tiene el formato esperado.";
		}
		FieldError primero = errores.get(0);
		String campo = etiquetaLegible(primero.getField());
		Object escrito = primero.getRejectedValue();
		String loEscrito = (escrito == null || escrito.toString().isBlank()) ? "" : " (se escribió \"" + escrito + "\")";
		if (esNumeroEntero(primero)) {
			return "El campo " + campo + " solo admite números enteros" + loEscrito + ".";
		}
		return "El campo " + campo + " no tiene el formato esperado" + loEscrito + ".";
	}

	private boolean esNumeroEntero(FieldError error) {
		String[] codigos = error.getCodes();
		if (codigos == null) {
			return false;
		}
		for (String codigo : codigos) {
			if (codigo.contains("Integer") || codigo.contains("Long")) {
				return true;
			}
		}
		return false;
	}

	/** "unidadesPorCaja" se lee mucho mejor como "unidades por caja". */
	private String etiquetaLegible(String campo) {
		String conEspacios = campo.replaceAll("([a-z])([A-Z])", "$1 $2").toLowerCase();
		return conEspacios.startsWith("id ") ? conEspacios.substring(3) : conEspacios;
	}

	/**
	 * Devuelve al usuario a la pantalla desde la que envió el formulario. Si el
	 * fallo viene de esa misma pantalla se va al panel, para no quedar dando
	 * vueltas entre redirecciones.
	 */
	private String volverAtras(HttpServletRequest peticion) {
		String origen = peticion.getHeader("Referer");
		if (origen == null || origen.isBlank() || origen.endsWith(peticion.getRequestURI())) {
			return "redirect:/";
		}
		return "redirect:" + origen;
	}
}
