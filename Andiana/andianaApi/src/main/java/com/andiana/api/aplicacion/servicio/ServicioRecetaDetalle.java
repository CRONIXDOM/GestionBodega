package com.andiana.api.aplicacion.servicio;

import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.Validar;
import com.andiana.api.dominio.modelo.RecetaDetalle;
import com.andiana.api.dominio.puerto.MateriaPrimaRepositorio;
import com.andiana.api.dominio.puerto.RecetaDetalleRepositorio;
import com.andiana.api.dominio.puerto.RecetaRepositorio;

public class ServicioRecetaDetalle extends ServicioCrud<RecetaDetalle> {

	private final RecetaDetalleRepositorio detalles;
	private final RecetaRepositorio recetas;
	private final MateriaPrimaRepositorio materias;

	public ServicioRecetaDetalle(RecetaDetalleRepositorio detalles, RecetaRepositorio recetas,
			MateriaPrimaRepositorio materias) {
		super(detalles, "Detalle de receta");
		this.detalles = detalles;
		this.recetas = recetas;
		this.materias = materias;
	}

	@Override
	protected void validar(RecetaDetalle detalle) {
		Validar.obligatorio(detalle.getIdReceta(), "receta");
		Validar.obligatorio(detalle.getIdMateriaPrima(), "materia prima");
		Validar.mayorQueCero(detalle.getCantidad(), "cantidad");

		if (recetas.buscarPorId(detalle.getIdReceta()).isEmpty()) {
			throw new ReglaNegocioException("La receta indicada no existe");
		}
		if (materias.buscarPorId(detalle.getIdMateriaPrima()).isEmpty()) {
			throw new ReglaNegocioException("La materia prima indicada no existe");
		}

		// la misma materia prima dos veces en una receta daria dos cantidades
		// distintas para lo mismo: se corrige la linea que ya existe
		boolean repetida = detalles.buscarPorReceta(detalle.getIdReceta()).stream()
				.filter(otro -> detalle.getIdRecetaDetalle() == null
						|| !detalle.getIdRecetaDetalle().equals(otro.getIdRecetaDetalle()))
				.anyMatch(otro -> detalle.getIdMateriaPrima().equals(otro.getIdMateriaPrima()));
		if (repetida) {
			throw new ReglaNegocioException("Esa materia prima ya esta en la receta: edita la cantidad de esa linea");
		}
	}
}
