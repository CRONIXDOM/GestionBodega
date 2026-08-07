package com.andiana.api.aplicacion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.andiana.api.aplicacion.servicio.ServicioMovimientoInventario;
import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.modelo.MateriaPrima;
import com.andiana.api.dominio.modelo.MovimientoInventario;

/**
 * Estas pruebas no levantan Spring ni tocan PostgreSQL: construyen el caso de
 * uso a mano con repositorios en memoria. Es la ventaja concreta de que la capa
 * de aplicacion no dependa de ningun framework.
 */
class ServicioMovimientoInventarioTest {

	private MateriasEnMemoria materias;
	private MovimientosEnMemoria movimientos;
	private ServicioMovimientoInventario servicio;
	private Integer idAzucar;

	@BeforeEach
	void prepararBodega() {
		materias = new MateriasEnMemoria();
		movimientos = new MovimientosEnMemoria();
		servicio = new ServicioMovimientoInventario(movimientos, materias);

		MateriaPrima azucar = new MateriaPrima(null, "AZUCAR", "KILOGRAMO", BigDecimal.ZERO);
		idAzucar = materias.guardar(azucar).getIdMateriaPrima();
	}

	private MovimientoInventario mover(String tipo, String cantidad, String fecha) {
		return servicio.guardar(new MovimientoInventario(null, idAzucar, tipo,
				new BigDecimal(cantidad), LocalDate.parse(fecha), null));
	}

	private BigDecimal stock() {
		return materias.buscarPorId(idAzucar).orElseThrow().getStock();
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
				.isInstanceOf(ReglaNegocioException.class)
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
		MovimientoInventario ingreso = mover("INGRESO", "100", "2026-08-01");
		mover("AJUSTE", "55", "2026-08-05");

		ingreso.setCantidad(new BigDecimal("200"));
		servicio.guardar(ingreso);

		// el ajuste es posterior, asi que sigue mandando el
		assertThat(stock()).isEqualByComparingTo("55");
	}

	@Test
	void borrarUnMovimientoAnteriorTampocoPisaElAjustePosterior() {
		MovimientoInventario ingreso = mover("INGRESO", "100", "2026-08-01");
		mover("AJUSTE", "55", "2026-08-05");

		servicio.eliminar(ingreso.getIdMovimiento());

		assertThat(stock()).isEqualByComparingTo("55");
	}

	@Test
	void borrarUnIngresoDevuelveElStockAlValorAnterior() {
		mover("INGRESO", "100", "2026-08-01");
		MovimientoInventario extra = mover("INGRESO", "40", "2026-08-02");

		servicio.eliminar(extra.getIdMovimiento());

		assertThat(stock()).isEqualByComparingTo("100");
	}

	@Test
	void editarUnIngresoNoPuedeDejarUnConsumoPosteriorSinRespaldo() {
		MovimientoInventario ingreso = mover("INGRESO", "100", "2026-08-01");
		mover("CONSUMO", "90", "2026-08-02");

		ingreso.setCantidad(new BigDecimal("10"));
		assertThatThrownBy(() -> servicio.guardar(ingreso))
				.isInstanceOf(ReglaNegocioException.class)
				.hasMessageContaining("No hay stock suficiente");

		assertThat(stock()).isEqualByComparingTo("10");
	}

	@Test
	void elTipoDelMovimientoTieneQueSerUnoDeLosTres() {
		assertThatThrownBy(() -> mover("REGALO", "10", "2026-08-01"))
				.isInstanceOf(ReglaNegocioException.class)
				.hasMessageContaining("INGRESO, CONSUMO, AJUSTE");
	}

	@Test
	void seAceptaElTipoEscritoEnMinusculas() {
		mover("ingreso", "10", "2026-08-01");

		assertThat(stock()).isEqualByComparingTo("10");
	}
}
