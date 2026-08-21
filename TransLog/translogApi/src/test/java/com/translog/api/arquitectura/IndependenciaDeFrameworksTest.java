package com.translog.api.arquitectura;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * La independencia de frameworks no es una intencion escrita en un documento:
 * esta prueba la comprueba. Lee el codigo fuente de las capas de dominio y de
 * aplicacion y falla si alguna de ellas importa Spring, JPA o Hibernate.
 *
 * Si alguien mete una anotacion de framework en el dominio, esta prueba se
 * pone en rojo y explica exactamente en que archivo.
 */
class IndependenciaDeFrameworksTest {

	private static final Path FUENTES = Path.of("src/main/java/com/translog/api");

	private static final List<String> PROHIBIDOS = List.of(
			"org.springframework", "jakarta.persistence", "org.hibernate", "jakarta.validation");

	private List<String> importacionesProhibidas(String capa, List<String> prohibidos) throws IOException {
		List<String> hallazgos = new ArrayList<>();
		try (Stream<Path> archivos = Files.walk(FUENTES.resolve(capa))) {
			for (Path archivo : archivos.filter(p -> p.toString().endsWith(".java")).toList()) {
				for (String linea : Files.readAllLines(archivo)) {
					if (!linea.startsWith("import ")) {
						continue;
					}
					for (String prohibido : prohibidos) {
						if (linea.contains(prohibido)) {
							hallazgos.add(FUENTES.relativize(archivo) + " -> " + linea.trim());
						}
					}
				}
			}
		}
		return hallazgos;
	}

	@Test
	@DisplayName("El dominio no depende de ningun framework")
	void elDominioEsJavaPuro() throws IOException {
		assertThat(importacionesProhibidas("dominio", PROHIBIDOS))
				.as("el dominio debe poder compilarse sin Spring ni JPA")
				.isEmpty();
	}

	@Test
	@DisplayName("Los casos de uso no dependen de JPA ni de Hibernate")
	void laAplicacionNoSabeDePersistencia() throws IOException {
		// lo unico que se admite de Spring es @Transactional, para que un movimiento
		// y el stock que recalcula se guarden o se deshagan juntos
		assertThat(importacionesProhibidas("aplicacion",
				List.of("jakarta.persistence", "org.hibernate", "org.springframework.data")))
				.as("las reglas de negocio no deben saber como se guardan los datos")
				.isEmpty();
	}

	@Test
	@DisplayName("Los controladores no tocan las entidades de la base")
	void laPersistenciaEstaAislada() throws IOException {
		assertThat(importacionesProhibidas("presentacion",
				List.of("jakarta.persistence", "org.hibernate")))
				.as("los controladores hablan de DTOs y del dominio, no de tablas")
				.isEmpty();
	}
}
