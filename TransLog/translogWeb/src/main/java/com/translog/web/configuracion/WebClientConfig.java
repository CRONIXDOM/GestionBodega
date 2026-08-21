package com.translog.web.configuracion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	/**
	 * La direccion de translogApi se lee de application.properties, no va escrita
	 * en el codigo: asi el proyecto se puede levantar en otro puerto o en otra
	 * maquina sin recompilar nada.
	 */
	@Value("${translog.api.url}")
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
