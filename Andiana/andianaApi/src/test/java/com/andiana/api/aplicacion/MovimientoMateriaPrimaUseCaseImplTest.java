package com.andiana.api.aplicacion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.andiana.api.aplicacion.casosuso.impl.MovimientoMateriaPrimaUseCaseImpl;
import com.andiana.api.dominio.entidades.MateriaPrima;
import com.andiana.api.dominio.entidades.MovimientoMateriaPrima;

/**
 * Estas pruebas no levantan Spring ni tocan PostgreSQL: construyen el caso de
 * uso a mano con repositorios en memoria. Es la ventaja concreta de que la capa
 * de aplicacion no dependa de ningun framework.
 */
class MovimientoMateriaPrimaUseCaseImplTest {

	private MateriasEnMemoria materias;
	private MovimientoMateriaPrimaUseCaseImpl useCase;
	private Integer idAzucar;
	private Integer idAgua;

	@BeforeEach
	void prepararBodega() {
		materias = new MateriasEnMemoria();
		useCase = new MovimientoMateriaPrimaUseCaseImpl(new MovimientosEnMemoria(), materias);

		idAzucar = materias.guardar(new MateriaPrima(null, "AZUCAR", "KILOGRAMO",
				BigDecimal.ZERO, BigDecimal.ZERO)).getIdMateria();
		idAgua = materias.guardar(new MateriaPrima(null, "AGUA PURIFICADA", "LITRO",
				BigDecimal.ZERO, BigDecimal.ZERO)).getIdMateria();
	}

	private BigDecimal stockDe(Integer idMateria) {
		return materias.buscarPorid(idMateria).orElseThrow().getStockActual();
	}

	private MovimientoMateriaPrima mover(String tipo, String cantidad, String fecha) {
		return useCase.guardar(new MovimientoMateriaPrima(null, idAzucar,
				LocalDateTime.parse(fecha + "T08:00"), tipo, new BigDecimal(cantidad), null));
	}

	private BigDecimal stock() {
		return materias.buscarPorid(idAzucar).orElseThrow().getStockActual();
	}

	@Test
	void elIngresoSumaYElConsumoResta() {
		mover("INGRESO", "100", "2026-08-01");
		assertThat(stock()).isEqualByComparingTo("100");

		mover("CONSUMO", "30", "2026-08-02");
		assertThat(stock()).isEqualByComparingTo("70");
	}

	@Test
	void noSePuedeConsumirMasDeLoQueHay() {
		mover("INGRESO", "50", "2026-08-01");

		assertThatThrownBy(() -> mover("CONSUMO", "80", "2026-08-02"))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("No hay stock suficiente");

		// y el intento fallido no dejo el stock a medias
		assertThat(stock()).isEqualByComparingTo("50");
	}

	@Test
	void elAjusteDejaElStockEnLaCantidadIndicada() {
		mover("INGRESO", "100", "2026-08-01");
		mover("AJUSTE", "55", "2026-08-02");

		assertThat(stock()).isEqualByComparingTo("55");
	}

	@Test
	void editarUnMovimientoAnteriorNoPisaUnAjustePosterior() {
		MovimientoMateriaPrima ingreso = mover("INGRESO", "100", "2026-08-01");
		mover("AJUSTE", "55", "2026-08-05");

		ingreso.setCantidad(new BigDecimal("200"));
		useCase.guardar(ingreso);

		// el ajuste es posterior, asi que sigue mandando el
		assertThat(stock()).isEqualByComparingTo("55");
	}

	@Test
	void borrarUnMovimientoAnteriorTampocoPisaElAjustePosterior() {
		MovimientoMateriaPrima ingreso = mover("INGRESO", "100", "2026-08-01");
		mover("AJUSTE", "55", "2026-08-05");

		useCase.eliminar(ingreso.getIdMovimiento());

		assertThat(stock()).isEqualByComparingTo("55");
	}

	@Test
	void borrarUnIngresoDevuelveElStockAlValorAnterior() {
		mover("INGRESO", "100", "2026-08-01");
		MovimientoMateriaPrima extra = mover("INGRESO", "40", "2026-08-02");

		useCase.eliminar(extra.getIdMovimiento());

		assertThat(stock()).isEqualByComparingTo("100");
	}

	@Test
	void editarUnIngresoNoPuedeDejarUnConsumoPosteriorSinRespaldo() {
		MovimientoMateriaPrima ingreso = mover("INGRESO", "100", "2026-08-01");
		mover("CONSUMO", "90", "2026-08-02");

		ingreso.setCantidad(new BigDecimal("10"));
		assertThatThrownBy(() -> useCase.guardar(ingreso))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("No hay stock suficiente");

		assertThat(stock()).isEqualByComparingTo("10");
	}

	@Test
	void elTipoDelMovimientoTieneQueSerUnoDeLosTres() {
		assertThatThrownBy(() -> mover("REGALO", "10", "2026-08-01"))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("INGRESO, CONSUMO, AJUSTE");
	}

	@Test
	void seAceptaElTipoEscritoEnMinusculas() {
		mover("ingreso", "10", "2026-08-01");

		assertThat(stock()).isEqualByComparingTo("10");
	}

	@Test
	void laTablaNoAdmiteCantidadCeroEnNingunTipo() {
		// la tabla movimiento_materia_prima tiene CHECK(cantidad > 0), asi que el
		// caso de uso lo rechaza antes de llegar a la base
		assertThatThrownBy(() -> mover("AJUSTE", "0", "2026-08-01"))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("mayor que cero");
	}

	// ---------- cambiar un movimiento de una materia prima a otra ----------

	@Test
	void moverUnMovimientoDeMateriaCorrigeLasDosBodegas() {
		MovimientoMateriaPrima ingreso = mover("INGRESO", "100", "2026-08-01");
		assertThat(stockDe(idAzucar)).isEqualByComparingTo("100");

		// estaba anotado en la materia equivocada
		ingreso.setIdMateria(idAgua);
		useCase.guardar(ingreso);

		assertThat(stockDe(idAzucar)).as("la materia que lo pierde tambien se recalcula")
				.isEqualByComparingTo("0");
		assertThat(stockDe(idAgua)).isEqualByComparingTo("100");
	}

	@Test
	void noSePuedeLlevarseUnIngresoDelQueDependeUnConsumoPosterior() {
		MovimientoMateriaPrima ingreso = mover("INGRESO", "100", "2026-08-01");
		mover("CONSUMO", "80", "2026-08-02");

		ingreso.setIdMateria(idAgua);
		assertThatThrownBy(() -> useCase.guardar(ingreso))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("No hay stock suficiente");

		// ninguna de las dos bodegas queda tocada
		assertThat(stockDe(idAzucar)).isEqualByComparingTo("20");
		assertThat(stockDe(idAgua)).isEqualByComparingTo("0");
	}

	@Test
	void moverUnConsumoDevuelveElStockAQuienYaNoLoGasta() {
		mover("INGRESO", "100", "2026-08-01");
		MovimientoMateriaPrima consumo = mover("CONSUMO", "30", "2026-08-02");
		assertThat(stockDe(idAzucar)).isEqualByComparingTo("70");

		// ese consumo era de la otra materia, y alli entra como ingreso
		consumo.setIdMateria(idAgua);
		consumo.setTipo("INGRESO");
		useCase.guardar(consumo);

		assertThat(stockDe(idAzucar)).isEqualByComparingTo("100");
		assertThat(stockDe(idAgua)).isEqualByComparingTo("30");
	}

	@Test
	void borrarUnIngresoDelQueDependeUnConsumoSeRechaza() {
		MovimientoMateriaPrima ingreso = mover("INGRESO", "100", "2026-08-01");
		mover("CONSUMO", "80", "2026-08-02");

		assertThatThrownBy(() -> useCase.eliminar(ingreso.getIdMovimiento()))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("No hay stock suficiente");
	}

	// ---------- stock que ya venia cargado en la base ----------

	@Test
	void elStockQueYaExistiaSeConservaComoSaldoInicial() {
		// una materia prima con stock cargado directamente en la base, sin ningun
		// movimiento que lo respalde
		MateriaPrima harina = materias.guardar(new MateriaPrima(null, "HARINA", "KILOGRAMO",
				new BigDecimal("700"), BigDecimal.ZERO));

		useCase.guardar(new MovimientoMateriaPrima(null, harina.getIdMateria(),
				LocalDateTime.parse("2026-08-06T10:00"), "CONSUMO", new BigDecimal("200"), null));

		// el stock no se perdio: quedo 700 - 200
		assertThat(stockDe(harina.getIdMateria())).isEqualByComparingTo("500");
	}

	@Test
	void elSaldoInicialQuedaAnotadoEnElHistorial() {
		MateriaPrima harina = materias.guardar(new MateriaPrima(null, "HARINA", "KILOGRAMO",
				new BigDecimal("700"), BigDecimal.ZERO));

		useCase.guardar(new MovimientoMateriaPrima(null, harina.getIdMateria(),
				LocalDateTime.parse("2026-08-06T10:00"), "INGRESO", new BigDecimal("100"), null));

		assertThat(useCase.listarTodos())
				.filteredOn(m -> m.getIdMateria().equals(harina.getIdMateria()))
				.extracting(MovimientoMateriaPrima::getObservacion)
				.contains("SALDO INICIAL");
		assertThat(stockDe(harina.getIdMateria())).isEqualByComparingTo("800");
	}

	@Test
	void unaMateriaPrimaSinStockPreviaNoInventaSaldoInicial() {
		mover("INGRESO", "50", "2026-08-01");

		assertThat(useCase.listarTodos()).hasSize(1);
	}
}
