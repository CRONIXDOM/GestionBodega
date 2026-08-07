package com.andiana.api.aplicacion.servicio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.Validar;
import com.andiana.api.dominio.modelo.MateriaPrima;
import com.andiana.api.dominio.modelo.MovimientoInventario;
import com.andiana.api.dominio.puerto.MateriaPrimaRepositorio;
import com.andiana.api.dominio.puerto.MovimientoInventarioRepositorio;

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
public class ServicioMovimientoInventario extends ServicioCrud<MovimientoInventario> {

	public static final String INGRESO = "INGRESO";
	public static final String CONSUMO = "CONSUMO";
	public static final String AJUSTE = "AJUSTE";

	private final MovimientoInventarioRepositorio movimientos;
	private final MateriaPrimaRepositorio materias;

	public ServicioMovimientoInventario(MovimientoInventarioRepositorio movimientos,
			MateriaPrimaRepositorio materias) {
		super(movimientos, "Movimiento de inventario");
		this.movimientos = movimientos;
		this.materias = materias;
	}

	@Override
	protected void validar(MovimientoInventario movimiento) {
		movimiento.setTipo(Validar.normalizar(movimiento.getTipo()));
		movimiento.setObservacion(Validar.normalizar(movimiento.getObservacion()));

		Validar.obligatorio(movimiento.getIdMateriaPrima(), "materia prima");
		Validar.unoDe(movimiento.getTipo(), "tipo", INGRESO, CONSUMO, AJUSTE);
		Validar.obligatorio(movimiento.getFecha(), "fecha");
		Validar.obligatorio(movimiento.getCantidad(), "cantidad");

		if (materias.buscarPorId(movimiento.getIdMateriaPrima()).isEmpty()) {
			throw new ReglaNegocioException("La materia prima indicada no existe");
		}

		// un ajuste puede dejar el stock en cero (se conto la bodega y no habia
		// nada); un ingreso o un consumo de cero no moverian nada
		if (AJUSTE.equals(movimiento.getTipo())) {
			if (movimiento.getCantidad().signum() < 0) {
				throw new ReglaNegocioException("El ajuste no puede dejar el stock en negativo");
			}
		} else {
			Validar.mayorQueCero(movimiento.getCantidad(), "cantidad");
		}
	}

	/** Guardar el movimiento y dejar el stock en lo que dice el historial. */
	@Override
	public MovimientoInventario guardar(MovimientoInventario movimiento) {
		if (movimiento == null) {
			throw new ReglaNegocioException("No llegaron los datos del movimiento");
		}
		validar(movimiento);

		// se comprueba ANTES de guardar: si el historial resultante no cuadra, no
		// se toca nada
		reproducir(historialConEsteMovimiento(movimiento), movimiento.getIdMateriaPrima());

		MovimientoInventario guardado = movimientos.guardar(movimiento);
		recalcularStock(movimiento.getIdMateriaPrima());
		return guardado;
	}

	/** Al borrar un movimiento el stock vuelve a lo que diga el resto del historial. */
	@Override
	public void eliminar(Integer id) {
		MovimientoInventario movimiento = buscarPorId(id);
		movimientos.eliminar(id);
		recalcularStock(movimiento.getIdMateriaPrima());
	}

	private void recalcularStock(Integer idMateriaPrima) {
		MateriaPrima materia = materia(idMateriaPrima);
		materia.setStock(reproducir(movimientos.buscarPorMateriaPrima(idMateriaPrima), idMateriaPrima));
		materias.guardar(materia);
	}

	/**
	 * El historial que quedaria si se guardara este movimiento: sustituye el
	 * suyo propio cuando se esta editando, o se agrega al final cuando es nuevo.
	 */
	private List<MovimientoInventario> historialConEsteMovimiento(MovimientoInventario movimiento) {
		List<MovimientoInventario> historial = new ArrayList<>();
		for (MovimientoInventario otro : movimientos.buscarPorMateriaPrima(movimiento.getIdMateriaPrima())) {
			if (!otro.getIdMovimiento().equals(movimiento.getIdMovimiento())) {
				historial.add(otro);
			}
		}
		historial.add(movimiento);
		historial.sort(Comparator.comparing(MovimientoInventario::getFecha)
				.thenComparing(m -> m.getIdMovimiento() == null ? Integer.MAX_VALUE : m.getIdMovimiento()));
		return historial;
	}

	/**
	 * Pasa la pelicula del historial y devuelve el stock final. Si en algun punto
	 * un consumo dejaria el stock en negativo se corta ahi mismo, para poder decir
	 * exactamente cuanto habia y cuanto se pedia.
	 */
	private BigDecimal reproducir(List<MovimientoInventario> historial, Integer idMateriaPrima) {
		BigDecimal stock = BigDecimal.ZERO;
		for (MovimientoInventario m : historial) {
			switch (m.getTipo()) {
				case INGRESO -> stock = stock.add(m.getCantidad());
				case CONSUMO -> {
					BigDecimal resultado = stock.subtract(m.getCantidad());
					if (resultado.signum() < 0) {
						MateriaPrima materia = materia(idMateriaPrima);
						throw new ReglaNegocioException("No hay stock suficiente de " + materia.getNombre()
								+ ": el " + m.getFecha() + " habria " + stock + " "
								+ materia.getUnidadMedida() + " y se quieren consumir " + m.getCantidad());
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
