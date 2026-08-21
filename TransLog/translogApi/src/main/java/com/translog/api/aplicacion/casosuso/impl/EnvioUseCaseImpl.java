package com.translog.api.aplicacion.casosuso.impl;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import com.translog.api.aplicacion.casosuso.entrada.IEnvioUseCase;
import com.translog.api.aplicacion.util.Validaciones;
import com.translog.api.dominio.entidades.Envio;
import com.translog.api.dominio.repositorio.ICiudadRepositorio;
import com.translog.api.dominio.repositorio.IEnvioRepositorio;

public class EnvioUseCaseImpl implements IEnvioUseCase {

	public static final String REGISTRADO = "REGISTRADO";
	public static final String EN_TRANSITO = "EN_TRANSITO";
	public static final String ENTREGADO = "ENTREGADO";
	public static final String CON_NOVEDAD = "CON_NOVEDAD";

	private final IEnvioRepositorio repositorio;
	private final ICiudadRepositorio ciudadRepositorio;

	public EnvioUseCaseImpl(IEnvioRepositorio repositorio, ICiudadRepositorio ciudadRepositorio) {
		this.repositorio = repositorio;
		this.ciudadRepositorio = ciudadRepositorio;
	}

	@Override
	public Envio guardar(Envio nuevoEnvio) {
		nuevoEnvio.setEstado(Validaciones.normalizar(nuevoEnvio.getEstado()));

		Validaciones.obligatorio(nuevoEnvio.getIdCiudadOrigen(), "ciudad de origen");
		Validaciones.obligatorio(nuevoEnvio.getIdCiudadDestino(), "ciudad de destino");
		Validaciones.mayorQueCero(nuevoEnvio.getPeso(), "peso");
		Validaciones.obligatorio(nuevoEnvio.getValorDeclarado(), "valor declarado");
		Validaciones.unoDe(nuevoEnvio.getEstado(), "estado", REGISTRADO, EN_TRANSITO, ENTREGADO, CON_NOVEDAD);

		if (nuevoEnvio.getValorDeclarado().signum() < 0) {
			throw new RuntimeException("El valor declarado no puede ser negativo");
		}
		if (nuevoEnvio.getIdCiudadOrigen().equals(nuevoEnvio.getIdCiudadDestino())) {
			throw new RuntimeException("El origen y el destino no pueden ser la misma ciudad");
		}
		existe(nuevoEnvio.getIdCiudadOrigen(), "origen");
		existe(nuevoEnvio.getIdCiudadDestino(), "destino");

		if (nuevoEnvio.getFechaRegistro() == null) {
			nuevoEnvio.setFechaRegistro(LocalDate.now());
		}

		// el envio se asigna a un despacho desde el despacho, que es donde se
		// puede comprobar la regla entera; aqui no se toca esa asignacion
		if (nuevoEnvio.getIdEnvio() != null) {
			Envio actual = buscarPorId(nuevoEnvio.getIdEnvio());
			nuevoEnvio.setIdDespacho(actual.getIdDespacho());
			if (actual.getIdDespacho() != null && cambioDeCiudades(actual, nuevoEnvio)) {
				throw new RuntimeException("Este envío ya está asignado al despacho "
						+ actual.getIdDespacho() + ": no se le puede cambiar el origen ni el destino");
			}
		} else {
			nuevoEnvio.setIdDespacho(null);
		}

		return repositorio.guardar(nuevoEnvio);
	}

	private boolean cambioDeCiudades(Envio actual, Envio nuevo) {
		return !actual.getIdCiudadOrigen().equals(nuevo.getIdCiudadOrigen())
				|| !actual.getIdCiudadDestino().equals(nuevo.getIdCiudadDestino());
	}

	private void existe(Integer idCiudad, String cual) {
		if (ciudadRepositorio.buscarPorId(idCiudad).isEmpty()) {
			throw new RuntimeException("La ciudad de " + cual + " indicada no existe");
		}
	}

	@Override
	public Envio buscarPorId(int idEnvio) {
		return repositorio.buscarPorId(idEnvio).orElseThrow(() -> new RuntimeException("Envío no encontrado"));
	}

	@Override
	public List<Envio> listarTodos() {
		return repositorio.listarTodos().stream()
				.sorted(Comparator.comparing(Envio::getIdEnvio,
						Comparator.nullsLast(Comparator.reverseOrder())))
				.toList();
	}

	/** Un envío que ya viaja en un despacho no se borra: primero se lo saca de él. */
	@Override
	public void eliminar(int idEnvio) {
		Envio envio = buscarPorId(idEnvio);
		if (envio.getIdDespacho() != null) {
			throw new RuntimeException("No se puede eliminar: el envío está asignado al despacho "
					+ envio.getIdDespacho() + ". Quítalo de ese despacho primero.");
		}
		repositorio.eliminar(idEnvio);
	}

}
