package com.translog.api.aplicacion.casosuso.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.translog.api.aplicacion.casosuso.entrada.IConsultaUseCase;
import com.translog.api.dominio.entidades.Ciudad;
import com.translog.api.dominio.entidades.Conductor;
import com.translog.api.dominio.entidades.Despacho;
import com.translog.api.dominio.entidades.Envio;
import com.translog.api.dominio.entidades.Ruta;
import com.translog.api.dominio.entidades.Vehiculo;
import com.translog.api.dominio.repositorio.ICiudadRepositorio;
import com.translog.api.dominio.repositorio.IConductorRepositorio;
import com.translog.api.dominio.repositorio.IDespachoRepositorio;
import com.translog.api.dominio.repositorio.IEnvioRepositorio;
import com.translog.api.dominio.repositorio.IRutaRepositorio;
import com.translog.api.dominio.repositorio.IVehiculoRepositorio;
import com.translog.api.presentacion.dto.response.DespachoConEnviosDto;

/**
 * La consulta que pide la gerencia: que despachos mueven mas envios.
 *
 * El enunciado pide "considerar unicamente atenciones validas segun la regla de
 * negocio", asi que no basta con contar: la regla se vuelve a comprobar sobre
 * los datos de hoy. Un despacho se guardo cumpliendo, pero despues alguien pudo
 * mandar el vehiculo a mantenimiento o inhabilitar al conductor; ese despacho ya
 * no es valido y no debe contarse.
 */
public class ConsultaUseCaseImpl implements IConsultaUseCase {

	private final IDespachoRepositorio despachoRepositorio;
	private final IEnvioRepositorio envioRepositorio;
	private final IRutaRepositorio rutaRepositorio;
	private final IVehiculoRepositorio vehiculoRepositorio;
	private final IConductorRepositorio conductorRepositorio;
	private final ICiudadRepositorio ciudadRepositorio;

	public ConsultaUseCaseImpl(IDespachoRepositorio despachoRepositorio, IEnvioRepositorio envioRepositorio,
			IRutaRepositorio rutaRepositorio, IVehiculoRepositorio vehiculoRepositorio,
			IConductorRepositorio conductorRepositorio, ICiudadRepositorio ciudadRepositorio) {
		this.despachoRepositorio = despachoRepositorio;
		this.envioRepositorio = envioRepositorio;
		this.rutaRepositorio = rutaRepositorio;
		this.vehiculoRepositorio = vehiculoRepositorio;
		this.conductorRepositorio = conductorRepositorio;
		this.ciudadRepositorio = ciudadRepositorio;
	}

	@Override
	public List<DespachoConEnviosDto> despachosConMasEnvios() {
		Map<Integer, String> nombreDeCiudad = new HashMap<>();
		for (Ciudad ciudad : ciudadRepositorio.listarTodos()) {
			nombreDeCiudad.put(ciudad.getIdCiudad(), ciudad.getNombre());
		}
		Map<Integer, Ruta> rutas = new HashMap<>();
		for (Ruta ruta : rutaRepositorio.listarTodos()) {
			rutas.put(ruta.getIdRuta(), ruta);
		}
		Map<Integer, Vehiculo> vehiculos = new HashMap<>();
		for (Vehiculo vehiculo : vehiculoRepositorio.listarTodos()) {
			vehiculos.put(vehiculo.getIdVehiculo(), vehiculo);
		}
		Map<Integer, Conductor> conductores = new HashMap<>();
		for (Conductor conductor : conductorRepositorio.listarTodos()) {
			conductores.put(conductor.getIdConductor(), conductor);
		}

		Map<Integer, List<Envio>> enviosPorDespacho = new HashMap<>();
		for (Envio envio : envioRepositorio.listarTodos()) {
			if (envio.getIdDespacho() != null) {
				enviosPorDespacho.computeIfAbsent(envio.getIdDespacho(), k -> new ArrayList<>()).add(envio);
			}
		}

		List<DespachoConEnviosDto> lineas = new ArrayList<>();
		for (Despacho despacho : despachoRepositorio.listarTodos()) {
			Ruta ruta = rutas.get(despacho.getIdRuta());
			Vehiculo vehiculo = vehiculos.get(despacho.getIdVehiculo());
			Conductor conductor = conductores.get(despacho.getIdConductor());
			List<Envio> envios = enviosPorDespacho.getOrDefault(despacho.getIdDespacho(), List.of());

			if (!cumpleLaRegla(ruta, vehiculo, conductor, envios)) {
				continue;
			}

			lineas.add(new DespachoConEnviosDto(despacho.getIdDespacho(), despacho.getFechaDespacho(),
					(long) envios.size(), nombreDeRuta(ruta, nombreDeCiudad),
					vehiculo.getPlaca(), conductor.getNombre(), despacho.getEstado()));
		}

		// de mas envios a menos; a igual cantidad, primero el mas reciente
		lineas.sort(Comparator.comparing(DespachoConEnviosDto::getCantidadEnvios).reversed()
				.thenComparing(DespachoConEnviosDto::getFechaDespacho, Comparator.reverseOrder()));
		return lineas;
	}

	/** La misma regla con la que se registro el despacho, revisada sobre los datos de hoy. */
	private boolean cumpleLaRegla(Ruta ruta, Vehiculo vehiculo, Conductor conductor, List<Envio> envios) {
		if (ruta == null || vehiculo == null || conductor == null || envios.isEmpty()) {
			return false;
		}
		if (!VehiculoUseCaseImpl.DISPONIBLE.equals(vehiculo.getEstado())
				|| !ConductorUseCaseImpl.HABILITADO.equals(conductor.getEstado())) {
			return false;
		}

		BigDecimal peso = BigDecimal.ZERO;
		for (Envio envio : envios) {
			if (!envio.getIdCiudadOrigen().equals(ruta.getIdCiudadOrigen())
					|| !envio.getIdCiudadDestino().equals(ruta.getIdCiudadDestino())) {
				return false;
			}
			peso = peso.add(envio.getPeso());
		}
		return peso.compareTo(vehiculo.getCapacidadMaxima()) <= 0;
	}

	private String nombreDeRuta(Ruta ruta, Map<Integer, String> nombreDeCiudad) {
		return nombreDeCiudad.getOrDefault(ruta.getIdCiudadOrigen(), "?") + " → "
				+ nombreDeCiudad.getOrDefault(ruta.getIdCiudadDestino(), "?");
	}

}
