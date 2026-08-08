package com.andiana.api.aplicacion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.andiana.api.aplicacion.casosuso.impl.DetalleRecetaUseCaseImpl;
import com.andiana.api.dominio.entidades.DetalleReceta;
import com.andiana.api.dominio.entidades.MateriaPrima;
import com.andiana.api.dominio.entidades.RecetaProduccion;

/**
 * Una receta lleva varias materias primas, asi que se tienen que poder cargar
 * todas juntas y no de a una.
 */
class DetalleRecetaUseCaseImplTest {

	private DetalleRecetaUseCaseImpl useCase;
	private DetallesEnMemoria detalles;
	private Integer receta;
	private Integer agua;
	private Integer azucar;
	private Integer colorante;

	@BeforeEach
	void prepararReceta() {
		detalles = new DetallesEnMemoria();
		RecetasEnMemoria recetas = new RecetasEnMemoria();
		MateriasEnMemoria materias = new MateriasEnMemoria();
		useCase = new DetalleRecetaUseCaseImpl(detalles, recetas, materias);

		receta = recetas.guardar(new RecetaProduccion(null, 1, 1, LocalDate.parse("2026-01-15"), true)).getIdReceta();

		agua = materias.guardar(new MateriaPrima(null, "AGUA PURIFICADA", "LITRO",
				new BigDecimal("100"), BigDecimal.ZERO)).getIdMateria();
		azucar = materias.guardar(new MateriaPrima(null, "AZUCAR", "KILOGRAMO",
				new BigDecimal("50"), BigDecimal.ZERO)).getIdMateria();
		colorante = materias.guardar(new MateriaPrima(null, "COLORANTE CARAMELO", "GRAMO",
				new BigDecimal("900"), BigDecimal.ZERO)).getIdMateria();
	}

	private DetalleReceta linea(Integer idMateria, String cantidad) {
		return new DetalleReceta(null, receta, idMateria, new BigDecimal(cantidad), null);
	}

	@Test
	void cargaTodaLaFormulaDeUnaSolaVez() {
		List<DetalleReceta> guardadas = useCase.guardarVarias(List.of(
				linea(agua, "0.320"), linea(azucar, "0.035"), linea(colorante, "1.200")));

		assertThat(guardadas).hasSize(3);
		assertThat(detalles.buscarPorReceta(receta)).hasSize(3);
	}

	@Test
	void laUnidadLaPoneCadaMateriaPrima() {
		useCase.guardarVarias(List.of(linea(agua, "0.320"), linea(azucar, "0.035")));

		assertThat(detalles.buscarPorReceta(receta)).extracting(DetalleReceta::getUnidad)
				.containsExactly("LITRO", "KILOGRAMO");
	}

	@Test
	void noDejaRepetirLaMismaMateriaPrimaDentroDelFormulario() {
		assertThatThrownBy(() -> useCase.guardarVarias(List.of(
				linea(agua, "0.320"), linea(azucar, "0.035"), linea(agua, "0.100"))))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("AGUA PURIFICADA");
	}

	@Test
	void siUnaLineaFallaNoEntraNingunaOtra() {
		assertThatThrownBy(() -> useCase.guardarVarias(List.of(
				linea(agua, "0.320"), linea(9999, "1"))))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("La materia prima indicada no existe");

		assertThat(detalles.buscarPorReceta(receta)).isEmpty();
	}

	@Test
	void noDejaRepetirUnaMateriaPrimaQueYaEstabaGuardada() {
		useCase.guardarVarias(List.of(linea(agua, "0.320")));

		assertThatThrownBy(() -> useCase.guardarVarias(List.of(linea(agua, "0.500"))))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("ya está en la receta");
	}

	@Test
	void pideAlMenosUnaMateriaPrima() {
		assertThatThrownBy(() -> useCase.guardarVarias(List.of()))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Agrega al menos una materia prima");
	}

	@Test
	void alEditarUnaLineaNoSeChocaConsigoMisma() {
		useCase.guardarVarias(List.of(linea(agua, "0.320"), linea(azucar, "0.035")));
		DetalleReceta guardada = detalles.buscarPorReceta(receta).get(0);

		guardada.setCantidad(new BigDecimal("0.400"));
		useCase.guardar(guardada);

		assertThat(useCase.buscarPorId(guardada.getIdDetalle()).getCantidad()).isEqualByComparingTo("0.400");
	}
}
