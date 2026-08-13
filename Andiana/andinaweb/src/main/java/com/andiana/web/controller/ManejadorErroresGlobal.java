package com.andiana.web.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.andiana.web.util.MensajesError;

/**
 * Red de seguridad de toda la aplicacion.
 *
 * Los listados consultan andianaApi al abrirse. Si la API no esta levantada
 * -o no logro conectarse a PostgreSQL- esa llamada falla y, sin nadie que la
 * atrape, Spring responde con su pagina de error y un 500. El usuario ve una
 * pantalla en blanco y no tiene forma de saber que lo que falta es levantar el
 * backend.
 *
 * Aqui se atrapa y se explica: que paso, y que hay que hacer para arreglarlo.
 */
@ControllerAdvice
public class ManejadorErroresGlobal {

	/** La API no contesta: casi siempre es que no esta levantada. */
	@ExceptionHandler(WebClientRequestException.class)
	public String apiCaida(Model model) {
		model.addAttribute("titulo", "No se pudo conectar con andianaApi");
		model.addAttribute("detalle", "La aplicación web está funcionando, pero no encuentra al backend.");
		model.addAttribute("comoArreglarlo", true);
		return "/Error/error";
	}

	/** Un id que no es un numero, tipicamente al escribir la URL a mano. */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public String direccionInvalida(Model model) {
		model.addAttribute("titulo", "La dirección solicitada no es válida");
		model.addAttribute("detalle", "Revisa el enlace: el identificador que trae no es un número.");
		return "/Error/error";
	}

	/**
	 * Cualquier otro fallo no previsto. Los "no encontrado" se dejan pasar para
	 * que sigan respondiendo 404 y no se conviertan en otra cosa.
	 */
	@ExceptionHandler(Exception.class)
	public String falloInesperado(Exception ex, Model model) throws Exception {
		if (ex instanceof NoResourceFoundException) {
			throw ex;
		}
		model.addAttribute("titulo", "No se pudo completar la operación");
		model.addAttribute("detalle", MensajesError.extraer(ex));
		return "/Error/error";
	}

}
