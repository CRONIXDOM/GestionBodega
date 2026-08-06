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

	/**
	 * Al borrar, el fallo mas habitual es que otro registro sigue usando este
	 * (clave foranea). El backend devuelve un error tecnico de base de datos, asi
	 * que se traduce a una explicacion que el usuario pueda entender y resolver.
	 */
	public static String alEliminar(Exception ex) {
		String tecnico = extraer(ex);
		String enMinusculas = tecnico == null ? "" : tecnico.toLowerCase();
		if (enMinusculas.contains("constraint") || enMinusculas.contains("foreign key")
				|| enMinusculas.contains("viola") || enMinusculas.contains("referenc")
				|| enMinusculas.contains("integrity")) {
			return "No se puede eliminar: este registro está siendo usado por otros datos del sistema. "
					+ "Elimina primero lo que depende de él.";
		}
		return "No se pudo eliminar. " + tecnico;
	}

	private static String limpiar(String mensaje) {
		if (mensaje.length() > 220) {
			return mensaje.substring(0, 220) + "...";
		}
		return mensaje;
	}
}
