package com.andiana.api.aplicacion.casosuso.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.andiana.api.aplicacion.casosuso.entrada.IDetalleRecetaUseCase;
import com.andiana.api.aplicacion.util.Validaciones;
import com.andiana.api.dominio.entidades.DetalleReceta;
import com.andiana.api.dominio.entidades.MateriaPrima;
import com.andiana.api.dominio.repositorio.IDetalleRecetaRepositorio;
import com.andiana.api.dominio.repositorio.IMateriaPrimaRepositorio;
import com.andiana.api.dominio.repositorio.IRecetaProduccionRepositorio;

public class DetalleRecetaUseCaseImpl implements IDetalleRecetaUseCase {

	private final IDetalleRecetaRepositorio repositorio;
	private final IRecetaProduccionRepositorio recetaRepositorio;
	private final IMateriaPrimaRepositorio materiaRepositorio;

	public DetalleRecetaUseCaseImpl(IDetalleRecetaRepositorio repositorio,
			IRecetaProduccionRepositorio recetaRepositorio, IMateriaPrimaRepositorio materiaRepositorio) {
		this.repositorio = repositorio;
		this.recetaRepositorio = recetaRepositorio;
		this.materiaRepositorio = materiaRepositorio;
	}

	@Override
	public DetalleReceta guardar(DetalleReceta nuevoDetalleReceta) {
		revisar(nuevoDetalleReceta, List.of());

		return repositorio.guardar(nuevoDetalleReceta);
	}

	/**
	 * Carga de una sola vez todas las materias primas de una receta, que es como
	 * se trabaja de verdad: una formula no se arma insumo por insumo.
	 *
	 * Se revisan TODAS las lineas antes de guardar ninguna, y el metodo es
	 * transaccional: o entra la formula completa o no entra nada. Si una linea
	 * falla, no queda media receta cargada.
	 */
	@Override
	@Transactional
	public List<DetalleReceta> guardarVarias(List<DetalleReceta> lineas) {
		if (lineas == null || lineas.isEmpty()) {
			throw new RuntimeException("Agrega al menos una materia prima a la receta");
		}

		List<DetalleReceta> revisadas = new ArrayList<>();
		for (DetalleReceta linea : lineas) {
			revisar(linea, revisadas);
			revisadas.add(linea);
		}

		List<DetalleReceta> guardadas = new ArrayList<>();
		for (DetalleReceta linea : revisadas) {
			guardadas.add(repositorio.guardar(linea));
		}
		return guardadas;
	}

	/**
	 * Las reglas de una linea de receta.
	 *
	 * @param acompanantes las otras lineas que vienen en el mismo envio, para
	 *                     poder detectar que la misma materia prima se repita
	 *                     dentro del propio formulario y no solo contra lo que ya
	 *                     estaba guardado.
	 */
	private void revisar(DetalleReceta detalle, List<DetalleReceta> acompanantes) {
		Validaciones.obligatorio(detalle.getIdReceta(), "receta");
		Validaciones.obligatorio(detalle.getIdMateria(), "materia prima");
		Validaciones.mayorQueCero(detalle.getCantidad(), "cantidad");

		if (recetaRepositorio.buscarPorId(detalle.getIdReceta()).isEmpty()) {
			throw new RuntimeException("La receta indicada no existe");
		}
		MateriaPrima materia = materiaRepositorio.buscarPorId(detalle.getIdMateria())
				.orElseThrow(() -> new RuntimeException("La materia prima indicada no existe"));

		detalle.setUnidad(materia.getUnidadMedida());

		boolean yaEstaGuardada = repositorio.buscarPorReceta(detalle.getIdReceta()).stream()
				.filter(otro -> detalle.getIdDetalle() == null
						|| !detalle.getIdDetalle().equals(otro.getIdDetalle()))
				.anyMatch(otro -> detalle.getIdMateria().equals(otro.getIdMateria()));
		if (yaEstaGuardada) {
			throw new RuntimeException("La materia prima " + materia.getNombre()
					+ " ya está en la receta: edita la cantidad de esa línea");
		}

		boolean repetidaEnElFormulario = acompanantes.stream()
				.anyMatch(otro -> detalle.getIdReceta().equals(otro.getIdReceta())
						&& detalle.getIdMateria().equals(otro.getIdMateria()));
		if (repetidaEnElFormulario) {
			throw new RuntimeException("Agregaste " + materia.getNombre()
					+ " dos veces: déjala una sola vez con la cantidad total");
		}
	}

	@Override
	public DetalleReceta buscarPorId(int idDetalle) {
		return repositorio.buscarPorId(idDetalle)
				.orElseThrow(() -> new RuntimeException("Detalle de receta no encontrado"));
	}

	/**
	 * Lo ultimo registrado va arriba. El listado se pagina, asi que en orden
	 * ascendente lo que se acaba de crear cae en la ultima pagina: el usuario
	 * vuelve del formulario, no lo ve, y cree que no se guardo.
	 */
	@Override
	public List<DetalleReceta> listarTodos() {
		return repositorio.listarTodos().stream()
				.sorted(Comparator.comparing(DetalleReceta::getIdDetalle,
						Comparator.nullsLast(Comparator.reverseOrder())))
				.toList();
	}

	@Override
	public void eliminar(int idDetalle) {
		buscarPorId(idDetalle);
		repositorio.eliminar(idDetalle);
	}
}
