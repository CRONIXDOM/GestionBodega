package com.translog.api.aplicacion;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.translog.api.aplicacion.casosuso.impl.ConsultaUseCaseImpl;
import com.translog.api.aplicacion.casosuso.impl.DespachoUseCaseImpl;
import com.translog.api.dominio.entidades.Ciudad;
import com.translog.api.dominio.entidades.Conductor;
import com.translog.api.dominio.entidades.Despacho;
import com.translog.api.dominio.entidades.Envio;
import com.translog.api.dominio.entidades.Ruta;
import com.translog.api.dominio.entidades.Vehiculo;
import com.translog.api.presentacion.dto.response.DespachoConEnviosDto;

/** La consulta que pide la gerencia: qué despachos mueven más envíos. */
class ConsultaUseCaseImplTest {

	private ConsultaUseCaseImpl consulta;
	private DespachoUseCaseImpl despachoUseCase;
	private VehiculosEnMemoria vehiculos;
	private ConductorsEnMemoria conductores;

	private Integer rutaQuitoGuayaquil;
	private Integer camion;
	private Integer habilitado;
	private Integer quito;
	private Integer guayaquil;
	private EnviosEnMemoria envios;

	@BeforeEach
	void prepararDespachos() {
		DespachosEnMemoria despachos = new DespachosEnMemoria();
		envios = new EnviosEnMemoria();
		vehiculos = new VehiculosEnMemoria();
		conductores = new ConductorsEnMemoria();
		CiudadsEnMemoria ciudades = new CiudadsEnMemoria();
		RutasEnMemoria rutas = new RutasEnMemoria();

		despachoUseCase = new DespachoUseCaseImpl(despachos, rutas, vehiculos, conductores, envios);
		consulta = new ConsultaUseCaseImpl(despachos, envios, rutas, vehiculos, conductores, ciudades);

		quito = ciudades.guardar(new Ciudad(null, "QUITO")).getIdCiudad();
		guayaquil = ciudades.guardar(new Ciudad(null, "GUAYAQUIL")).getIdCiudad();
		rutaQuitoGuayaquil = rutas.guardar(new Ruta(null, quito, guayaquil, new BigDecimal("420"))).getIdRuta();
		camion = vehiculos.guardar(new Vehiculo(null, "PBA-1234", new BigDecimal("5000"), "DISPONIBLE"))
				.getIdVehiculo();
		habilitado = conductores.guardar(new Conductor(null, "LUIS ANDRADE", "LIC-001", "HABILITADO"))
				.getIdConductor();
	}

	private Integer envio(String peso) {
		return envios.guardar(new Envio(null, quito, guayaquil, new BigDecimal(peso),
				LocalDate.parse("2026-02-02"), new BigDecimal("100"), "REGISTRADO", null)).getIdEnvio();
	}

	private Despacho registrar(String fecha, int cuantosEnvios) {
		List<Integer> ids = new java.util.ArrayList<>();
		for (int i = 0; i < cuantosEnvios; i++) {
			ids.add(envio("50"));
		}
		return despachoUseCase.guardar(new Despacho(null, LocalDate.parse(fecha), rutaQuitoGuayaquil, camion,
				habilitado, "ACTIVO"), ids);
	}

	@Test
	@DisplayName("Ordena los despachos de más envíos a menos")
	void ordenaDeMasEnviosAMenos() {
		registrar("2026-02-10", 1);
		registrar("2026-02-11", 3);
		registrar("2026-02-12", 2);

		List<DespachoConEnviosDto> ranking = consulta.despachosConMasEnvios();

		assertThat(ranking).extracting(DespachoConEnviosDto::getCantidadEnvios)
				.containsExactly(3L, 2L, 1L);
	}

	@Test
	@DisplayName("Muestra la fecha, la cantidad y la ruta, que es lo que pide la gerencia")
	void muestraFechaCantidadYRuta() {
		registrar("2026-02-11", 2);

		DespachoConEnviosDto linea = consulta.despachosConMasEnvios().get(0);

		assertThat(linea.getFechaDespacho()).isEqualTo(LocalDate.parse("2026-02-11"));
		assertThat(linea.getCantidadEnvios()).isEqualTo(2L);
		assertThat(linea.getRuta()).isEqualTo("QUITO → GUAYAQUIL");
	}

	@Test
	@DisplayName("Un despacho deja de contar si su vehículo ya no está disponible")
	void siElVehiculoDejaDeEstarDisponible_yaNoCuenta() {
		registrar("2026-02-11", 2);
		assertThat(consulta.despachosConMasEnvios()).hasSize(1);

		Vehiculo taller = vehiculos.buscarPorId(camion).orElseThrow();
		taller.setEstado("MANTENIMIENTO");
		vehiculos.guardar(taller);

		assertThat(consulta.despachosConMasEnvios()).isEmpty();
	}

	@Test
	@DisplayName("Un despacho deja de contar si su conductor ya no está habilitado")
	void siElConductorSeInhabilita_yaNoCuenta() {
		registrar("2026-02-11", 2);

		Conductor suspendido = conductores.buscarPorId(habilitado).orElseThrow();
		suspendido.setEstado("NO_HABILITADO");
		conductores.guardar(suspendido);

		assertThat(consulta.despachosConMasEnvios()).isEmpty();
	}

	@Test
	@DisplayName("Sin despachos registrados la consulta no falla, sale vacía")
	void sinDespachos_saleVacia() {
		assertThat(consulta.despachosConMasEnvios()).isEmpty();
	}

}
