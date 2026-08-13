package com.andiana.api.aplicacion.casosuso.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.andiana.api.aplicacion.casosuso.entrada.IMovimientoMateriaPrimaUseCase;
import com.andiana.api.aplicacion.util.Validaciones;
import com.andiana.api.dominio.entidades.MateriaPrima;
import com.andiana.api.dominio.entidades.MovimientoMateriaPrima;
import com.andiana.api.dominio.repositorio.IMateriaPrimaRepositorio;
import com.andiana.api.dominio.repositorio.IMovimientoMateriaPrimaRepositorio;

public class MovimientoMateriaPrimaUseCaseImpl implements IMovimientoMateriaPrimaUseCase {

	public static final String INGRESO = "INGRESO";
	public static final String CONSUMO = "CONSUMO";
	public static final String AJUSTE = "AJUSTE";

	private final IMovimientoMateriaPrimaRepositorio repositorio;
	private final IMateriaPrimaRepositorio materiaRepositorio;

	public MovimientoMateriaPrimaUseCaseImpl(IMovimientoMateriaPrimaRepositorio repositorio,
			IMateriaPrimaRepositorio materiaRepositorio) {
		this.repositorio = repositorio;
		this.materiaRepositorio = materiaRepositorio;
	}

	@Override
	@Transactional
	public MovimientoMateriaPrima guardar(MovimientoMateriaPrima nuevoMovimientoMateriaPrima) {
		nuevoMovimientoMateriaPrima.setTipo(Validaciones.normalizar(nuevoMovimientoMateriaPrima.getTipo()));
		nuevoMovimientoMateriaPrima
				.setObservacion(Validaciones.normalizar(nuevoMovimientoMateriaPrima.getObservacion()));

		Validaciones.obligatorio(nuevoMovimientoMateriaPrima.getIdMateria(), "materia prima");
		Validaciones.unoDe(nuevoMovimientoMateriaPrima.getTipo(), "tipo", INGRESO, CONSUMO, AJUSTE);

		Validaciones.mayorQueCero(nuevoMovimientoMateriaPrima.getCantidad(), "cantidad");

		if (nuevoMovimientoMateriaPrima.getFecha() == null) {
			nuevoMovimientoMateriaPrima.setFecha(LocalDateTime.now());
		}
		if (materiaRepositorio.buscarPorId(nuevoMovimientoMateriaPrima.getIdMateria()).isEmpty()) {
			throw new RuntimeException("La materia prima indicada no existe");
		}

		asegurarSaldoInicial(nuevoMovimientoMateriaPrima.getIdMateria(), nuevoMovimientoMateriaPrima.getFecha());

		Integer materiaAnterior = null;
		if (nuevoMovimientoMateriaPrima.getIdMovimiento() != null) {
			int anterior = buscarPorId(nuevoMovimientoMateriaPrima.getIdMovimiento()).getIdMateria();
			if (anterior != nuevoMovimientoMateriaPrima.getIdMateria()) {
				materiaAnterior = anterior;
			}
		}

		reproducir(historialConEsteMovimiento(nuevoMovimientoMateriaPrima), nuevoMovimientoMateriaPrima.getIdMateria());
		if (materiaAnterior != null) {
			reproducir(historialSinEsteMovimiento(materiaAnterior, nuevoMovimientoMateriaPrima.getIdMovimiento()),
					materiaAnterior);
		}

		MovimientoMateriaPrima guardado = repositorio.guardar(nuevoMovimientoMateriaPrima);
		recalcularStock(nuevoMovimientoMateriaPrima.getIdMateria());
		if (materiaAnterior != null) {
			recalcularStock(materiaAnterior);
		}
		return guardado;
	}

	@Override
	public MovimientoMateriaPrima buscarPorId(int idMovimiento) {
		return repositorio.buscarPorId(idMovimiento)
				.orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
	}

	/**
	 * Lo ultimo registrado va arriba. El listado se pagina, asi que en orden
	 * ascendente lo que se acaba de crear cae en la ultima pagina: el usuario
	 * vuelve del formulario, no lo ve, y cree que no se guardo.
	 */
	@Override
	public List<MovimientoMateriaPrima> listarTodos() {
		return repositorio.listarTodos().stream()
				.sorted(Comparator.comparing(MovimientoMateriaPrima::getIdMovimiento,
						Comparator.nullsLast(Comparator.reverseOrder())))
				.toList();
	}

	@Override
	@Transactional
	public void eliminar(int idMovimiento) {
		MovimientoMateriaPrima movimiento = buscarPorId(idMovimiento);
		repositorio.eliminar(idMovimiento);
		recalcularStock(movimiento.getIdMateria());
	}

	private void recalcularStock(int idMateria) {
		MateriaPrima materia = materia(idMateria);
		materia.setStockActual(reproducir(repositorio.buscarPorMateria(idMateria), idMateria));
		materiaRepositorio.guardar(materia);
	}

	private List<MovimientoMateriaPrima> historialConEsteMovimiento(MovimientoMateriaPrima movimiento) {
		List<MovimientoMateriaPrima> historial = new ArrayList<>();
		for (MovimientoMateriaPrima otro : repositorio.buscarPorMateria(movimiento.getIdMateria())) {
			if (!otro.getIdMovimiento().equals(movimiento.getIdMovimiento())) {
				historial.add(otro);
			}
		}
		historial.add(movimiento);
		historial.sort(Comparator.comparing(MovimientoMateriaPrima::getFecha)
				.thenComparing(m -> m.getIdMovimiento() == null ? Integer.MAX_VALUE : m.getIdMovimiento()));
		return historial;
	}

	private void asegurarSaldoInicial(int idMateria, LocalDateTime fechaDelPrimero) {
		if (!repositorio.buscarPorMateria(idMateria).isEmpty()) {
			return;
		}
		MateriaPrima materia = materia(idMateria);
		BigDecimal saldo = materia.getStockActual();
		if (saldo == null || saldo.signum() <= 0) {
			return;
		}
		repositorio.guardar(new MovimientoMateriaPrima(null, idMateria, fechaDelPrimero.minusDays(1), INGRESO, saldo,
				"SALDO INICIAL"));
	}

	private List<MovimientoMateriaPrima> historialSinEsteMovimiento(int idMateria, Integer idMovimiento) {
		return repositorio.buscarPorMateria(idMateria).stream()
				.filter(otro -> !otro.getIdMovimiento().equals(idMovimiento)).toList();
	}

	private BigDecimal reproducir(List<MovimientoMateriaPrima> historial, int idMateria) {
		BigDecimal stock = BigDecimal.ZERO;
		for (MovimientoMateriaPrima m : historial) {
			switch (m.getTipo()) {
			case INGRESO -> stock = stock.add(m.getCantidad());
			case CONSUMO -> {
				BigDecimal resultado = stock.subtract(m.getCantidad());
				if (resultado.signum() < 0) {
					MateriaPrima materia = materia(idMateria);
					throw new RuntimeException(
							"No hay stock suficiente de " + materia.getNombre() + ": el " + m.getFecha().toLocalDate()
									+ " habría " + Validaciones.legible(stock) + " " + materia.getUnidadMedida()
									+ " y se quieren consumir " + Validaciones.legible(m.getCantidad()));
				}
				stock = resultado;
			}
			default -> stock = m.getCantidad();
			}
		}
		return stock;
	}

	private MateriaPrima materia(int idMateria) {
		return materiaRepositorio.buscarPorId(idMateria)
				.orElseThrow(() -> new RuntimeException("La materia prima indicada no existe"));
	}
}
