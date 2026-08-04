package com.bodega.controlweb.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}

	// define ruta base del backend "control" (sin prefijo /api)
	@Bean
	WebClient webCliente(WebClient.Builder builder) {
		return builder.baseUrl("http://localhost:8080").build();
	}

}
