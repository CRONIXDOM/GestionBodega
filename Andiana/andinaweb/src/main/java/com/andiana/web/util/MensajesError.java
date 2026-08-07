package com.andiana.web.util;

import org.springframework.web.reactive.function.client.WebClientResponseException;

import tools.jackson.databind.ObjectMapper;

/**
 * andianaApi contesta los rechazos con {"message": "..."}. Aquí se saca ese
 * texto para poder mostrárselo al usuario tal cual, en vez de un error técnico.
 */
public final class MensajesError {

	private static final ObjectMapper JSON = new ObjectMapper();

	private MensajesError() {
	}

	public static String extraer(Exception ex) {
		if (ex instanceof WebClientResponseException respuesta) {
			try {
				var cuerpo = JSON.readValue(respuesta.getResponseBodyAsString(), java.util.Map.class);
				Object mensaje = cuerpo.get("message");
				if (mensaje != null && !mensaje.toString().isBlank()) {
					return mensaje.toString();
				}
			} catch (Exception noEraJson) {
				// se cae al mensaje genérico de abajo
			}
		}
		Throwable causa = ex;
		while (causa.getCause() != null && causa.getMessage() == null) {
			causa = causa.getCause();
		}
		return causa.getMessage() == null ? "No se pudo completar la operación" : causa.getMessage();
	}

	/** Cuando el borrado falla suele ser porque otro registro depende de este. */
	public static String alEliminar(Exception ex) {
		String mensaje = extraer(ex);
		if (mensaje.toLowerCase().contains("constraint") || mensaje.toLowerCase().contains("foreign key")) {
			return "No se puede eliminar: este registro está siendo usado por otros datos del sistema.";
		}
		return mensaje;
	}
}
