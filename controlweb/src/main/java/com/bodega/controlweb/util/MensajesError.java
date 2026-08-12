package com.bodega.controlweb.util;

import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 * Convierte los errores que devuelve el backend (via WebClient) en un mensaje
 * legible para mostrar en pantalla, en vez de dejar que la excepción se
 * propague hasta la página de error genérica de Spring.
 *
 * Las validaciones del backend ya vienen escritas para el usuario y se muestran
 * tal cual. Lo que nunca debe llegar a pantalla es un error crudo de la base de
 * datos ("could not execute statement [ERROR: ... violates foreign key
 * constraint ...]"): eso no le dice nada a quien está usando el sistema, así
 * que se traduce.
 */
public final class MensajesError {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private MensajesError() {
	}

	public static String extraer(Exception ex) {
		return traducir(mensajeCrudo(ex));
	}

	/**
	 * Al borrar, el fallo mas habitual es que otro registro sigue usando este
	 * (clave foranea). El backend devuelve un error tecnico de base de datos, asi
	 * que se traduce a una explicacion que el usuario pueda entender y resolver.
	 */
	public static String alEliminar(Exception ex) {
		String crudo = mensajeCrudo(ex);
		if (esDeIntegridad(crudo)) {
			return "No se puede eliminar: este registro está siendo usado por otros datos del sistema. "
					+ "Elimina primero lo que depende de él.";
		}
		return "No se pudo eliminar. " + traducir(crudo);
	}

	/** El mensaje tal como lo devolvio el backend, sin retocar. */
	private static String mensajeCrudo(Exception ex) {
		if (ex instanceof WebClientResponseException webEx) {
			String cuerpo = webEx.getResponseBodyAsString();
			try {
				JsonNode nodo = MAPPER.readTree(cuerpo);
				if (nodo.has("message") && !nodo.get("message").isNull()) {
					return nodo.get("message").asText();
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
	 * Si el mensaje viene de la base de datos se cambia por una explicacion
	 * entendible; si es una validacion del sistema se deja como esta, porque ya
	 * esta escrita para el usuario.
	 */
	private static String traducir(String mensaje) {
		if (mensaje == null || mensaje.isBlank()) {
			return "Ocurrió un error. Verifica los datos e intenta de nuevo.";
		}
		if (!esDeLaBaseDeDatos(mensaje)) {
			return limpiar(mensaje);
		}

		String texto = mensaje.toLowerCase();
		if (texto.contains("duplicate key") || texto.contains("llave duplicada")
				|| texto.contains("unique constraint")) {
			return "Ya existe un registro con esos datos. Revisa los campos que no se pueden repetir.";
		}
		if (esDeIntegridad(mensaje)) {
			return "No se pudo guardar: este registro está relacionado con otros datos del sistema "
					+ "y el cambio los dejaría inconsistentes.";
		}
		if (texto.contains("not-null") || texto.contains("not null") || texto.contains("no nulo")) {
			return "Falta completar un dato obligatorio.";
		}
		return "No se pudo completar la operación por un problema con los datos. "
				+ "Revisa la información e intenta de nuevo.";
	}

	private static boolean esDeLaBaseDeDatos(String mensaje) {
		String texto = mensaje.toLowerCase();
		return texto.contains("could not execute statement") || texto.contains("constraint")
				|| texto.contains("sql [") || texto.contains("violates") || texto.contains("viola ")
				|| texto.contains("integrity");
	}

	private static boolean esDeIntegridad(String mensaje) {
		String texto = mensaje == null ? "" : mensaje.toLowerCase();
		return texto.contains("constraint") || texto.contains("foreign key")
				|| texto.contains("viola") || texto.contains("referenc")
				|| texto.contains("integrity");
	}

	private static String limpiar(String mensaje) {
		if (mensaje.length() > 220) {
			return mensaje.substring(0, 220) + "...";
		}
		return mensaje;
	}
}
