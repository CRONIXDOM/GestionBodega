package com.translog.api.aplicacion.casosuso.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.translog.api.aplicacion.casosuso.entrada.IDespachoUseCase;
import com.translog.api.aplicacion.util.Validaciones;
import com.translog.api.dominio.entidades.Conductor;
import com.translog.api.dominio.entidades.Despacho;
import com.translog.api.dominio.entidades.Envio;
import com.translog.api.dominio.entidades.Ruta;
import com.translog.api.dominio.entidades.Vehiculo;
import com.translog.api.dominio.repositorio.IConductorRepositorio;
import com.translog.api.dominio.repositorio.IDespachoRepositorio;
import com.translog.api.dominio.repositorio.IEnvioRepositorio;
import com.translog.api.dominio.repositorio.IRutaRepositorio;
import com.translog.api.dominio.repositorio.IVehiculoRepositorio;

/**
 * Aquí vive la regla de negocio del caso, entera y en un solo sitio:
 *
 * "Un despacho podrá registrarse únicamente cuando tenga una ruta definida, un
 * vehículo en estado disponible y un conductor habilitado. Los envíos asociados
 * al despacho deben corresponder al origen y destino de la ruta seleccionada y
 * no pueden encontrarse asignados previamente a otro despacho activo o
 * finalizado. Además, la suma del peso de los envíos asignados no debe superar
 * la capacidad máxima del vehículo."
 *
 * Se comprueba TODO antes de guardar nada, y el método es transaccional: o
 * entra el despacho con sus envíos, o no entra nada. Guardar el despacho y
 * fallar al asignar los envíos dejaría un viaje vacío en la base.
 */
public class DespachoUseCaseImpl implements IDespachoUseCase {

	public static final String ACTIVO = "ACTIVO";
	public static final String FINALIZADO = "FINALIZADO";
	public static final String CANCELADO = "CANCELADO";

	private final IDespachoRepositorio repositorio;
	private final IRutaRepositorio rutaRepositorio;
	private final IVehiculoRepositorio vehiculoRepositorio;
	private final IConductorRepositorio conductorRepositorio;
	private final IEnvioRepositorio envioRepositorio;

	public DespachoUseCaseImpl(IDespachoRepositorio repositorio, IRutaRepositorio rutaRepositorio,
			IVehiculoRepositorio vehiculoRepositorio, IConductorRepositorio conductorRepositorio,
			IEnvioRepositorio envioRepositorio) {
		this.repositorio = repositorio;
		this.rutaRepositorio = rutaRepositorio;
		this.vehiculoRepositorio = vehiculoRepositorio;
		this.conductorRepositorio = conductorRepositorio;
		this.envioRepositorio = envioRepositorio;
	}

	@Override
	@Transactional
	public Despacho guardar(Despacho nuevoDespacho, List<Integer> idsDeEnvios) {
		nuevoDespacho.setEstado(Validaciones.normalizar(nuevoDespacho.getEstado()));

		Validaciones.obligatorio(nuevoDespacho.getIdRuta(), "ruta");
		Validaciones.obligatorio(nuevoDespacho.getIdVehiculo(), "vehículo");
		Validaciones.obligatorio(nuevoDespacho.getIdConductor(), "conductor");
		Validaciones.unoDe(nuevoDespacho.getEstado(), "estado", ACTIVO, FINALIZADO, CANCELADO);

		if (nuevoDespacho.getFechaDespacho() == null) {
			nuevoDespacho.setFechaDespacho(LocalDate.now());
		}

		// --- 1. una ruta definida
		Ruta ruta = rutaRepositorio.buscarPorId(nuevoDespacho.getIdRuta())
				.orElseThrow(() -> new RuntimeException("La ruta indicada no existe"));

		// --- 2. un vehiculo en estado disponible
		Vehiculo vehiculo = vehiculoRepositorio.buscarPorId(nuevoDespacho.getIdVehiculo())
				.orElseThrow(() -> new RuntimeException("El vehículo indicado no existe"));
		if (!VehiculoUseCaseImpl.DISPONIBLE.equals(vehiculo.getEstado())) {
			throw new RuntimeException("El vehículo " + vehiculo.getPlaca() + " no está disponible: está en "
					+ vehiculo.getEstado().toLowerCase().replace('_', ' '));
		}

		// --- 3. un conductor habilitado
		Conductor conductor = conductorRepositorio.buscarPorId(nuevoDespacho.getIdConductor())
				.orElseThrow(() -> new RuntimeException("El conductor indicado no existe"));
		if (!ConductorUseCaseImpl.HABILITADO.equals(conductor.getEstado())) {
			throw new RuntimeException("El conductor " + conductor.getNombre() + " no está habilitado");
		}

		List<Envio> envios = revisarLosEnvios(idsDeEnvios, ruta, vehiculo, nuevoDespacho.getIdDespacho());

		Despacho guardado = repositorio.guardar(nuevoDespacho);

		// los envíos que estaban y ya no vienen en la lista quedan libres otra vez
		if (nuevoDespacho.getIdDespacho() != null) {
			for (Envio anterior : enviosDelDespacho(guardado.getIdDespacho())) {
				if (envios.stream().noneMatch(e -> e.getIdEnvio().equals(anterior.getIdEnvio()))) {
					anterior.setIdDespacho(null);
					envioRepositorio.guardar(anterior);
				}
			}
		}
		for (Envio envio : envios) {
			envio.setIdDespacho(guardado.getIdDespacho());
			envioRepositorio.guardar(envio);
		}
		return guardado;
	}

	/**
	 * Las tres condiciones que la regla le pone a los envíos: que vayan por esa
	 * ruta, que estén libres, y que entre todos quepan en el vehículo.
	 *
	 * @param idDespachoQueSeEdita al editar, los envíos que ya son de este mismo
	 *                             despacho no cuentan como "asignados a otro".
	 */
	private List<Envio> revisarLosEnvios(List<Integer> idsDeEnvios, Ruta ruta, Vehiculo vehiculo,
			Integer idDespachoQueSeEdita) {

		if (idsDeEnvios == null || idsDeEnvios.isEmpty()) {
			throw new RuntimeException("Un despacho tiene que llevar al menos un envío");
		}

		List<Envio> envios = new ArrayList<>();
		BigDecimal pesoTotal = BigDecimal.ZERO;

		for (Integer idEnvio : idsDeEnvios) {
			if (envios.stream().anyMatch(e -> e.getIdEnvio().equals(idEnvio))) {
				throw new RuntimeException("El envío " + idEnvio + " está repetido en la lista");
			}
			Envio envio = envioRepositorio.buscarPorId(idEnvio)
					.orElseThrow(() -> new RuntimeException("El envío " + idEnvio + " no existe"));

			// --- 4. el envío tiene que ir por esa ruta
			if (!envio.getIdCiudadOrigen().equals(ruta.getIdCiudadOrigen())
					|| !envio.getIdCiudadDestino().equals(ruta.getIdCiudadDestino())) {
				throw new RuntimeException("El envío " + idEnvio
						+ " no corresponde al origen y destino de la ruta seleccionada");
			}

			// --- 5. el envío no puede venir de otro despacho
			if (envio.getIdDespacho() != null && !envio.getIdDespacho().equals(idDespachoQueSeEdita)) {
				throw new RuntimeException("El envío " + idEnvio + " ya está asignado al despacho "
						+ envio.getIdDespacho());
			}

			pesoTotal = pesoTotal.add(envio.getPeso());
			envios.add(envio);
		}

		// --- 6. entre todos tienen que caber en el vehículo
		if (pesoTotal.compareTo(vehiculo.getCapacidadMaxima()) > 0) {
			throw new RuntimeException("Los " + envios.size() + " envíos pesan "
					+ Validaciones.legible(pesoTotal) + " kg y el vehículo " + vehiculo.getPlaca()
					+ " carga como máximo " + Validaciones.legible(vehiculo.getCapacidadMaxima()) + " kg");
		}

		return envios;
	}

	@Override
	public Despacho buscarPorId(int idDespacho) {
		return repositorio.buscarPorId(idDespacho)
				.orElseThrow(() -> new RuntimeException("Despacho no encontrado"));
	}

	@Override
	public List<Despacho> listarTodos() {
		return repositorio.listarTodos().stream()
				.sorted(Comparator.comparing(Despacho::getIdDespacho,
						Comparator.nullsLast(Comparator.reverseOrder())))
				.toList();
	}

	/** Al borrar el despacho sus envíos vuelven a quedar libres, no se borran. */
	@Override
	@Transactional
	public void eliminar(int idDespacho) {
		buscarPorId(idDespacho);
		for (Envio envio : enviosDelDespacho(idDespacho)) {
			envio.setIdDespacho(null);
			envioRepositorio.guardar(envio);
		}
		repositorio.eliminar(idDespacho);
	}

	@Override
	public List<Envio> enviosDelDespacho(int idDespacho) {
		return envioRepositorio.listarTodos().stream()
				.filter(envio -> envio.getIdDespacho() != null && envio.getIdDespacho() == idDespacho)
				.sorted(Comparator.comparing(Envio::getIdEnvio))
				.toList();
	}

	@Override
	public List<Envio> enviosDisponiblesParaLaRuta(int idRuta, Integer idDespachoQueSeEdita) {
		Ruta ruta = rutaRepositorio.buscarPorId(idRuta)
				.orElseThrow(() -> new RuntimeException("La ruta indicada no existe"));

		return envioRepositorio.listarTodos().stream()
				.filter(envio -> envio.getIdCiudadOrigen().equals(ruta.getIdCiudadOrigen())
						&& envio.getIdCiudadDestino().equals(ruta.getIdCiudadDestino()))
				.filter(envio -> envio.getIdDespacho() == null
						|| envio.getIdDespacho().equals(idDespachoQueSeEdita))
				.sorted(Comparator.comparing(Envio::getIdEnvio))
				.toList();
	}

}
