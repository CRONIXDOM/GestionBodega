package com.andiana.web.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}

	// define la ruta base del backend "andianaApi"
	@Bean
	WebClient webCliente(WebClient.Builder builder) {
		return builder.baseUrl("http://localhost:8090").build();
	}

}
