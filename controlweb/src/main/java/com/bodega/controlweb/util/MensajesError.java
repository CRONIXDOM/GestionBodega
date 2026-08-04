package com.bodega.controlweb.util;

import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 * Convierte los errores que devuelve el backend (via WebClient) en un mensaje
 * legible para mostrar en pantalla, en vez de dejar que la excepción se
 * propague hasta la página de error genérica de Spring.
 */
public final class MensajesError {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private MensajesError() {
	}

	public static String extraer(Exception ex) {
		if (ex instanceof WebClientResponseException webEx) {
			String cuerpo = webEx.getResponseBodyAsString();
			try {
				JsonNode nodo = MAPPER.readTree(cuerpo);
				if (nodo.has("message") && !nodo.get("message").isNull()) {
					return limpiar(nodo.get("message").asText());
				}
			} catch (Exception ignorada) {
				// el cuerpo no es JSON valido, se usa el mensaje generico de abajo
			}
			return "El servidor respondió con un error (" + webEx.getStatusCode().value()
					+ "). Verifica los datos e intenta de nuevo.";
		}
		if (ex instanceof WebClientRequestException) {
			return "No se pudo conectar con el servidor. Verifica que el backend esté corriendo.";
		}
		return "Ocurrió un error al guardar. Verifica los datos e intenta de nuevo.";
	}

	private static String limpiar(String mensaje) {
		if (mensaje.length() > 220) {
			return mensaje.substring(0, 220) + "...";
		}
		return mensaje;
	}
}
