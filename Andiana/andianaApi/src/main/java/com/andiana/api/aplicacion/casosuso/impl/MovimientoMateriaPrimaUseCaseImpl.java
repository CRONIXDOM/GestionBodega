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

/**
 * El stock de una materia prima nunca se escribe a mano: siempre se vuelve a
 * calcular reproduciendo su historial de movimientos de principio a fin.
 *
 * Se hace asi, y no sumando y restando sobre el stock que hubiera, porque un
 * AJUSTE fija el stock en un valor absoluto: si despues se corrige o se borra
 * un movimiento anterior a ese ajuste, lo unico que da el resultado correcto es
 * volver a pasar la pelicula entera.
 *
 * INGRESO suma, CONSUMO resta y AJUSTE deja el stock en la cantidad indicada.
 */
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

		// la tabla exige cantidad > 0 para los tres tipos, asi que un ajuste a cero
		// no se puede registrar: para vaciar una materia prima se usa un CONSUMO
		Validaciones.mayorQueCero(nuevoMovimientoMateriaPrima.getCantidad(), "cantidad");

		if (nuevoMovimientoMateriaPrima.getFecha() == null) {
			nuevoMovimientoMateriaPrima.setFecha(LocalDateTime.now());
		}
		if (materiaRepositorio.buscarPorid(nuevoMovimientoMateriaPrima.getIdMateria()).isEmpty()) {
			throw new RuntimeException("La materia prima indicada no existe");
		}

		// se comprueba ANTES de guardar: si el historial resultante no cuadra, no
		// se toca nada
		reproducir(historialConEsteMovimiento(nuevoMovimientoMateriaPrima),
				nuevoMovimientoMateriaPrima.getIdMateria());

		MovimientoMateriaPrima guardado = repositorio.guardar(nuevoMovimientoMateriaPrima);
		recalcularStock(nuevoMovimientoMateriaPrima.getIdMateria());
		return guardado;
	}

	@Override
	public MovimientoMateriaPrima buscarPorId(int idMovimiento) {
		return repositorio.buscarPorid(idMovimiento)
				.orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
	}

	@Override
	public List<MovimientoMateriaPrima> listarTodos() {
		return repositorio.listarTodos();
	}

	/** Al borrar un movimiento el stock vuelve a lo que diga el resto del historial. */
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

	/**
	 * El historial que quedaria si se guardara este movimiento: sustituye el
	 * suyo propio cuando se esta editando, o se agrega al final cuando es nuevo.
	 */
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

	/**
	 * Pasa la pelicula del historial y devuelve el stock final. Si en algun punto
	 * un consumo dejaria el stock en negativo se corta ahi mismo, para poder decir
	 * exactamente cuanto habia y cuanto se pedia. La tabla ademas tiene un
	 * CHECK(stock_actual >= 0), asi que un negativo ni siquiera se podria guardar.
	 */
	private BigDecimal reproducir(List<MovimientoMateriaPrima> historial, int idMateria) {
		BigDecimal stock = BigDecimal.ZERO;
		for (MovimientoMateriaPrima m : historial) {
			switch (m.getTipo()) {
				case INGRESO -> stock = stock.add(m.getCantidad());
				case CONSUMO -> {
					BigDecimal resultado = stock.subtract(m.getCantidad());
					if (resultado.signum() < 0) {
						MateriaPrima materia = materia(idMateria);
						throw new RuntimeException("No hay stock suficiente de " + materia.getNombre()
								+ ": el " + m.getFecha().toLocalDate() + " habría " + Validaciones.legible(stock)
								+ " " + materia.getUnidadMedida() + " y se quieren consumir "
								+ Validaciones.legible(m.getCantidad()));
					}
					stock = resultado;
				}
				default -> stock = m.getCantidad();
			}
		}
		return stock;
	}

	private MateriaPrima materia(int idMateria) {
		return materiaRepositorio.buscarPorid(idMateria)
				.orElseThrow(() -> new RuntimeException("La materia prima indicada no existe"));
	}
}
