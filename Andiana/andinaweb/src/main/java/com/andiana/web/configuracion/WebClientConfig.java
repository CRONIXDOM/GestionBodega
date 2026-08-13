package com.andiana.web.configuracion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	/**
	 * La direccion de andianaApi se lee de application.properties, no va escrita
	 * en el codigo: asi el proyecto se puede levantar en otro puerto o en otra
	 * maquina sin recompilar nada.
	 */
	@Value("${andiana.api.url}")
	private String urlDeLaApi;

	@Bean
	WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}

	@Bean
	WebClient webCliente(WebClient.Builder builder) {
		return builder.baseUrl(urlDeLaApi).build();
	}

}
