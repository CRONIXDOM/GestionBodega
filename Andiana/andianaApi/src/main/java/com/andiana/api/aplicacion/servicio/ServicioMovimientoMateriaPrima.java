package com.andiana.api.aplicacion.servicio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.Validar;
import com.andiana.api.dominio.modelo.MateriaPrima;
import com.andiana.api.dominio.modelo.MovimientoMateriaPrima;
import com.andiana.api.dominio.puerto.MateriaPrimaRepositorio;
import com.andiana.api.dominio.puerto.MovimientoMateriaPrimaRepositorio;

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
public class ServicioMovimientoMateriaPrima extends ServicioCrud<MovimientoMateriaPrima> {

	public static final String INGRESO = "INGRESO";
	public static final String CONSUMO = "CONSUMO";
	public static final String AJUSTE = "AJUSTE";

	private final MovimientoMateriaPrimaRepositorio movimientos;
	private final MateriaPrimaRepositorio materias;

	public ServicioMovimientoMateriaPrima(MovimientoMateriaPrimaRepositorio movimientos,
			MateriaPrimaRepositorio materias) {
		super(movimientos, "Movimiento de materia prima");
		this.movimientos = movimientos;
		this.materias = materias;
	}

	@Override
	protected void validar(MovimientoMateriaPrima movimiento) {
		movimiento.setTipo(Validar.normalizar(movimiento.getTipo()));
		movimiento.setObservacion(Validar.normalizar(movimiento.getObservacion()));

		Validar.obligatorio(movimiento.getIdMateria(), "materia prima");
		Validar.unoDe(movimiento.getTipo(), "tipo", INGRESO, CONSUMO, AJUSTE);

		// la tabla exige cantidad > 0 para los tres tipos, asi que un ajuste a cero
		// no se puede registrar: para vaciar una materia prima se usa un CONSUMO
		Validar.mayorQueCero(movimiento.getCantidad(), "cantidad");

		if (movimiento.getFecha() == null) {
			movimiento.setFecha(LocalDateTime.now());
		}
		if (materias.buscarPorId(movimiento.getIdMateria()).isEmpty()) {
			throw new ReglaNegocioException("La materia prima indicada no existe");
		}
	}

	/** Guardar el movimiento y dejar el stock en lo que dice el historial. */
	@Override
	public MovimientoMateriaPrima guardar(MovimientoMateriaPrima movimiento) {
		if (movimiento == null) {
			throw new ReglaNegocioException("No llegaron los datos del movimiento");
		}
		validar(movimiento);

		// se comprueba ANTES de guardar: si el historial resultante no cuadra, no
		// se toca nada
		reproducir(historialConEsteMovimiento(movimiento), movimiento.getIdMateria());

		MovimientoMateriaPrima guardado = movimientos.guardar(movimiento);
		recalcularStock(movimiento.getIdMateria());
		return guardado;
	}

	/** Al borrar un movimiento el stock vuelve a lo que diga el resto del historial. */
	@Override
	public void eliminar(Integer id) {
		MovimientoMateriaPrima movimiento = buscarPorId(id);
		movimientos.eliminar(id);
		recalcularStock(movimiento.getIdMateria());
	}

	private void recalcularStock(Integer idMateria) {
		MateriaPrima materia = materia(idMateria);
		materia.setStockActual(reproducir(movimientos.buscarPorMateria(idMateria), idMateria));
		materias.guardar(materia);
	}

	/**
	 * El historial que quedaria si se guardara este movimiento: sustituye el
	 * suyo propio cuando se esta editando, o se agrega al final cuando es nuevo.
	 */
	private List<MovimientoMateriaPrima> historialConEsteMovimiento(MovimientoMateriaPrima movimiento) {
		List<MovimientoMateriaPrima> historial = new ArrayList<>();
		for (MovimientoMateriaPrima otro : movimientos.buscarPorMateria(movimiento.getIdMateria())) {
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
	private BigDecimal reproducir(List<MovimientoMateriaPrima> historial, Integer idMateria) {
		BigDecimal stock = BigDecimal.ZERO;
		for (MovimientoMateriaPrima m : historial) {
			switch (m.getTipo()) {
				case INGRESO -> stock = stock.add(m.getCantidad());
				case CONSUMO -> {
					BigDecimal resultado = stock.subtract(m.getCantidad());
					if (resultado.signum() < 0) {
						MateriaPrima materia = materia(idMateria);
						throw new ReglaNegocioException("No hay stock suficiente de " + materia.getNombre()
								+ ": el " + m.getFecha().toLocalDate() + " habria " + Validar.legible(stock)
								+ " " + materia.getUnidadMedida() + " y se quieren consumir "
								+ Validar.legible(m.getCantidad()));
					}
					stock = resultado;
				}
				default -> stock = m.getCantidad();
			}
		}
		return stock;
	}

	private MateriaPrima materia(Integer id) {
		return materias.buscarPorId(id)
				.orElseThrow(() -> new ReglaNegocioException("La materia prima indicada no existe"));
	}
}
