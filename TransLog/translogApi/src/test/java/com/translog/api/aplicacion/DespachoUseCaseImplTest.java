package com.translog.api.aplicacion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.translog.api.aplicacion.casosuso.impl.DespachoUseCaseImpl;
import com.translog.api.dominio.entidades.Ciudad;
import com.translog.api.dominio.entidades.Conductor;
import com.translog.api.dominio.entidades.Despacho;
import com.translog.api.dominio.entidades.Envio;
import com.translog.api.dominio.entidades.Ruta;
import com.translog.api.dominio.entidades.Vehiculo;

/**
 * La regla de negocio del caso, condición por condición:
 *
 * "Un despacho podrá registrarse únicamente cuando tenga una ruta definida, un
 * vehículo en estado disponible y un conductor habilitado. Los envíos asociados
 * al despacho deben corresponder al origen y destino de la ruta seleccionada y
 * no pueden encontrarse asignados previamente a otro despacho activo o
 * finalizado. Además, la suma del peso de los envíos asignados no debe superar
 * la capacidad máxima del vehículo."
 */
class DespachoUseCaseImplTest {

	private DespachoUseCaseImpl useCase;
	private DespachosEnMemoria despachos;
	private EnviosEnMemoria envios;
	private VehiculosEnMemoria vehiculos;
	private ConductorsEnMemoria conductores;

	private Integer quito;
	private Integer guayaquil;
	private Integer cuenca;
	private Integer rutaQuitoGuayaquil;
	private Integer camion;
	private Integer camioneta;
	private Integer enMantenimiento;
	private Integer habilitado;
	private Integer inhabilitado;

	@BeforeEach
	void prepararLaFlota() {
		despachos = new DespachosEnMemoria();
		envios = new EnviosEnMemoria();
		vehiculos = new VehiculosEnMemoria();
		conductores = new ConductorsEnMemoria();
		CiudadsEnMemoria ciudades = new CiudadsEnMemoria();
		RutasEnMemoria rutas = new RutasEnMemoria();

		useCase = new DespachoUseCaseImpl(despachos, rutas, vehiculos, conductores, envios);

		quito = ciudades.guardar(new Ciudad(null, "QUITO")).getIdCiudad();
		guayaquil = ciudades.guardar(new Ciudad(null, "GUAYAQUIL")).getIdCiudad();
		cuenca = ciudades.guardar(new Ciudad(null, "CUENCA")).getIdCiudad();

		rutaQuitoGuayaquil = rutas.guardar(new Ruta(null, quito, guayaquil, new BigDecimal("420"))).getIdRuta();

		camion = vehiculos.guardar(new Vehiculo(null, "PBA-1234", new BigDecimal("3500"), "DISPONIBLE"))
				.getIdVehiculo();
		camioneta = vehiculos.guardar(new Vehiculo(null, "GYE-5678", new BigDecimal("200"), "DISPONIBLE"))
				.getIdVehiculo();
		enMantenimiento = vehiculos.guardar(new Vehiculo(null, "AZU-9012", new BigDecimal("8000"), "MANTENIMIENTO"))
				.getIdVehiculo();

		habilitado = conductores.guardar(new Conductor(null, "LUIS ANDRADE", "LIC-001", "HABILITADO"))
				.getIdConductor();
		inhabilitado = conductores.guardar(new Conductor(null, "JORGE PAREDES", "LIC-003", "NO_HABILITADO"))
				.getIdConductor();
	}

	private Integer envio(Integer origen, Integer destino, String peso) {
		return envios.guardar(new Envio(null, origen, destino, new BigDecimal(peso), LocalDate.parse("2026-02-02"),
				new BigDecimal("100"), "REGISTRADO", null)).getIdEnvio();
	}

	private Despacho despacho(Integer idVehiculo, Integer idConductor) {
		return new Despacho(null, LocalDate.parse("2026-02-10"), rutaQuitoGuayaquil, idVehiculo, idConductor,
				"ACTIVO");
	}

	// ---------------------------------------------------------- se puede

	@Test
	@DisplayName("Con ruta, vehículo disponible, conductor habilitado y envíos que caben, se registra")
	void despachoValido_seRegistra() {
		Integer uno = envio(quito, guayaquil, "120");
		Integer dos = envio(quito, guayaquil, "80");

		Despacho guardado = useCase.guardar(despacho(camion, habilitado), List.of(uno, dos));

		assertThat(guardado.getIdDespacho()).isNotNull();
		assertThat(useCase.enviosDelDespacho(guardado.getIdDespacho())).hasSize(2);
	}

	@Test
	@DisplayName("Los envíos quedan enganchados al despacho")
	void losEnviosQuedanAsignados() {
		Integer uno = envio(quito, guayaquil, "120");

		Despacho guardado = useCase.guardar(despacho(camion, habilitado), List.of(uno));

		assertThat(envios.buscarPorId(uno).orElseThrow().getIdDespacho()).isEqualTo(guardado.getIdDespacho());
	}

	@Test
	@DisplayName("El peso justo en el límite entra")
	void pesoExactamenteIgualALaCapacidad_entra() {
		Integer uno = envio(quito, guayaquil, "200");

		assertThatCode(() -> useCase.guardar(despacho(camioneta, habilitado), List.of(uno)))
				.doesNotThrowAnyException();
	}

	// ---------------------------------------------------------- no se puede

	@Test
	@DisplayName("Un vehículo que no está disponible frena el despacho")
	void vehiculoNoDisponible_seRechaza() {
		Integer uno = envio(quito, guayaquil, "120");

		assertThatThrownBy(() -> useCase.guardar(despacho(enMantenimiento, habilitado), List.of(uno)))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("no está disponible");

		assertThat(despachos.listarTodos()).isEmpty();
		assertThat(envios.buscarPorId(uno).orElseThrow().getIdDespacho()).isNull();
	}

	@Test
	@DisplayName("Un conductor no habilitado frena el despacho")
	void conductorNoHabilitado_seRechaza() {
		Integer uno = envio(quito, guayaquil, "120");

		assertThatThrownBy(() -> useCase.guardar(despacho(camion, inhabilitado), List.of(uno)))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("no está habilitado");

		assertThat(despachos.listarTodos()).isEmpty();
	}

	@Test
	@DisplayName("Una ruta que no existe frena el despacho")
	void rutaInexistente_seRechaza() {
		Integer uno = envio(quito, guayaquil, "120");
		Despacho sinRuta = despacho(camion, habilitado);
		sinRuta.setIdRuta(999);

		assertThatThrownBy(() -> useCase.guardar(sinRuta, List.of(uno)))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("La ruta indicada no existe");
	}

	@Test
	@DisplayName("Un envío que va a otra ciudad no puede subirse a esta ruta")
	void envioDeOtraRuta_seRechaza() {
		Integer aGuayaquil = envio(quito, guayaquil, "120");
		Integer aCuenca = envio(quito, cuenca, "50");

		assertThatThrownBy(() -> useCase.guardar(despacho(camion, habilitado), List.of(aGuayaquil, aCuenca)))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("no corresponde al origen y destino de la ruta");

		assertThat(despachos.listarTodos()).isEmpty();
		assertThat(envios.buscarPorId(aGuayaquil).orElseThrow().getIdDespacho()).isNull();
	}

	@Test
	@DisplayName("Un envío que ya viaja en otro despacho no se puede reasignar")
	void envioYaAsignado_seRechaza() {
		Integer uno = envio(quito, guayaquil, "120");
		useCase.guardar(despacho(camion, habilitado), List.of(uno));

		assertThatThrownBy(() -> useCase.guardar(despacho(camion, habilitado), List.of(uno)))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("ya está asignado al despacho");

		assertThat(despachos.listarTodos()).hasSize(1);
	}

	@Test
	@DisplayName("Si entre todos pasan de la capacidad del vehículo, no se registra")
	void pesoSuperiorALaCapacidad_seRechaza() {
		Integer uno = envio(quito, guayaquil, "150");
		Integer dos = envio(quito, guayaquil, "80");

		assertThatThrownBy(() -> useCase.guardar(despacho(camioneta, habilitado), List.of(uno, dos)))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("carga como máximo");

		assertThat(despachos.listarTodos()).isEmpty();
	}

	@Test
	@DisplayName("Un despacho sin envíos no tiene sentido")
	void sinEnvios_seRechaza() {
		assertThatThrownBy(() -> useCase.guardar(despacho(camion, habilitado), List.of()))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("al menos un envío");
	}

	@Test
	@DisplayName("El mismo envío repetido en la lista se detecta")
	void envioRepetidoEnLaLista_seRechaza() {
		Integer uno = envio(quito, guayaquil, "120");

		assertThatThrownBy(() -> useCase.guardar(despacho(camion, habilitado), List.of(uno, uno)))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("repetido");
	}

	// ---------------------------------------------------------- al editar

	@Test
	@DisplayName("Al editar, sus propios envíos no cuentan como asignados a otro")
	void alEditar_susEnviosNoEstorban() {
		Integer uno = envio(quito, guayaquil, "120");
		Integer dos = envio(quito, guayaquil, "80");
		Despacho guardado = useCase.guardar(despacho(camion, habilitado), List.of(uno));

		guardado.setIdDespacho(guardado.getIdDespacho());
		useCase.guardar(guardado, List.of(uno, dos));

		assertThat(useCase.enviosDelDespacho(guardado.getIdDespacho())).hasSize(2);
	}

	@Test
	@DisplayName("El envío que se saca del despacho queda libre otra vez")
	void alQuitarUnEnvio_vuelveAQuedarLibre() {
		Integer uno = envio(quito, guayaquil, "120");
		Integer dos = envio(quito, guayaquil, "80");
		Despacho guardado = useCase.guardar(despacho(camion, habilitado), List.of(uno, dos));

		useCase.guardar(guardado, List.of(uno));

		assertThat(envios.buscarPorId(dos).orElseThrow().getIdDespacho()).isNull();
		assertThat(useCase.enviosDelDespacho(guardado.getIdDespacho())).hasSize(1);
	}

	@Test
	@DisplayName("Al borrar el despacho sus envíos quedan libres, no se borran")
	void alBorrarElDespacho_losEnviosSobreviven() {
		Integer uno = envio(quito, guayaquil, "120");
		Despacho guardado = useCase.guardar(despacho(camion, habilitado), List.of(uno));

		useCase.eliminar(guardado.getIdDespacho());

		assertThat(envios.buscarPorId(uno)).isPresent();
		assertThat(envios.buscarPorId(uno).orElseThrow().getIdDespacho()).isNull();
	}

	// ---------------------------------------------------------- ayuda al usuario

	@Test
	@DisplayName("Para una ruta se ofrecen solo los envíos que le corresponden y están libres")
	void enviosDisponibles_soloLosDeEsaRutaYSinDespacho() {
		Integer aGuayaquil = envio(quito, guayaquil, "120");
		envio(quito, cuenca, "50");
		Integer yaAsignado = envio(quito, guayaquil, "60");
		useCase.guardar(despacho(camion, habilitado), List.of(yaAsignado));

		List<Envio> disponibles = useCase.enviosDisponiblesParaLaRuta(rutaQuitoGuayaquil, null);

		assertThat(disponibles).extracting(Envio::getIdEnvio).containsExactly(aGuayaquil);
	}

}
