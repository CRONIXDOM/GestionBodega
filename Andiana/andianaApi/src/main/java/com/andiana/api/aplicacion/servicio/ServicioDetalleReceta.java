package com.andiana.api.aplicacion.servicio;

import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.Validar;
import com.andiana.api.dominio.modelo.DetalleReceta;
import com.andiana.api.dominio.modelo.MateriaPrima;
import com.andiana.api.dominio.puerto.DetalleRecetaRepositorio;
import com.andiana.api.dominio.puerto.MateriaPrimaRepositorio;
import com.andiana.api.dominio.puerto.RecetaProduccionRepositorio;

public class ServicioDetalleReceta extends ServicioCrud<DetalleReceta> {

	private final DetalleRecetaRepositorio detalles;
	private final RecetaProduccionRepositorio recetas;
	private final MateriaPrimaRepositorio materias;

	public ServicioDetalleReceta(DetalleRecetaRepositorio detalles, RecetaProduccionRepositorio recetas,
			MateriaPrimaRepositorio materias) {
		super(detalles, "Detalle de receta");
		this.detalles = detalles;
		this.recetas = recetas;
		this.materias = materias;
	}

	@Override
	protected void validar(DetalleReceta detalle) {
		Validar.obligatorio(detalle.getIdReceta(), "receta");
		Validar.obligatorio(detalle.getIdMateria(), "materia prima");
		Validar.mayorQueCero(detalle.getCantidad(), "cantidad");

		if (recetas.buscarPorId(detalle.getIdReceta()).isEmpty()) {
			throw new ReglaNegocioException("La receta indicada no existe");
		}
		MateriaPrima materia = materias.buscarPorId(detalle.getIdMateria())
				.orElseThrow(() -> new ReglaNegocioException("La materia prima indicada no existe"));

		// la unidad la define la materia prima: si se dejara escribir aparte se
		// podria pedir "2 LITROS" de algo que se mide en gramos
		detalle.setUnidad(materia.getUnidadMedida());

		// la misma materia prima dos veces en una receta daria dos cantidades
		// distintas para lo mismo
		boolean repetida = detalles.buscarPorReceta(detalle.getIdReceta()).stream()
				.filter(otro -> detalle.getIdDetalle() == null
						|| !detalle.getIdDetalle().equals(otro.getIdDetalle()))
				.anyMatch(otro -> detalle.getIdMateria().equals(otro.getIdMateria()));
		if (repetida) {
			throw new ReglaNegocioException("Esa materia prima ya esta en la receta: edita la cantidad de esa linea");
		}
	}
}
