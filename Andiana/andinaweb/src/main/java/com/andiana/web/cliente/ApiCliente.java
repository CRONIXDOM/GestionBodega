package com.andiana.web.cliente;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import tools.jackson.databind.ObjectMapper;

/**
 * Lo unico de esta web que habla con andianaApi. Los datos viajan como mapas
 * (nombre de campo -> valor) porque la API ya devuelve el JSON con esa forma:
 * asi no hay que repetir aqui las mismas clases que ya existen del otro lado.
 */
@Component
public class ApiCliente {

	private final WebClient cliente;
	private final ObjectMapper json = new ObjectMapper();

	public ApiCliente(@Value("${andiana.api.url}") String urlApi) {
		this.cliente = WebClient.builder().baseUrl(urlApi).build();
	}

	public List<Map<String, Object>> listar(String ruta) {
		return cliente.get().uri("/" + ruta).retrieve()
				.bodyToFlux(new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>() {
				})
				.collectList().block();
	}

	public Map<String, Object> buscar(String ruta, Object id) {
		return cliente.get().uri("/" + ruta + "/" + id).retrieve()
				.bodyToMono(new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>() {
				})
				.block();
	}

	public void guardar(String ruta, Map<String, Object> datos) {
		cliente.post().uri("/" + ruta).contentType(MediaType.APPLICATION_JSON).bodyValue(datos)
				.retrieve().toBodilessEntity().block();
	}

	public void eliminar(String ruta, Object id) {
		cliente.delete().uri("/" + ruta + "/" + id).retrieve().toBodilessEntity().block();
	}

	/**
	 * La API contesta los rechazos con {"message": "..."}. Aqui se saca ese texto
	 * para poder mostrarselo al usuario tal cual, en vez de un error tecnico.
	 */
	public String mensajeDe(Exception ex) {
		if (ex instanceof WebClientResponseException respuesta) {
			try {
				Map<?, ?> cuerpo = json.readValue(respuesta.getResponseBodyAsString(), Map.class);
				Object mensaje = cuerpo.get("message");
				if (mensaje != null && !mensaje.toString().isBlank()) {
					return mensaje.toString();
				}
			} catch (Exception noEraJson) {
				// se cae al mensaje generico de abajo
			}
		}
		Throwable causa = ex;
		while (causa.getCause() != null && causa.getMessage() == null) {
			causa = causa.getCause();
		}
		return causa.getMessage() == null ? "No se pudo completar la operacion" : causa.getMessage();
	}
}
