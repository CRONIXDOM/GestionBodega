package com.andiana.api.aplicacion.servicio;

import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.Validar;
import com.andiana.api.dominio.modelo.RecetaProduccion;
import com.andiana.api.dominio.puerto.DetalleRecetaRepositorio;
import com.andiana.api.dominio.puerto.ProductoRepositorio;
import com.andiana.api.dominio.puerto.RecetaProduccionRepositorio;

public class ServicioRecetaProduccion extends ServicioCrud<RecetaProduccion> {

	private final RecetaProduccionRepositorio recetas;
	private final ProductoRepositorio productos;
	private final DetalleRecetaRepositorio detalles;

	public ServicioRecetaProduccion(RecetaProduccionRepositorio recetas, ProductoRepositorio productos,
			DetalleRecetaRepositorio detalles) {
		super(recetas, "Receta");
		this.recetas = recetas;
		this.productos = productos;
		this.detalles = detalles;
	}

	@Override
	protected void validar(RecetaProduccion receta) {
		Validar.obligatorio(receta.getIdProducto(), "producto");
		Validar.mayorQueCero(receta.getVersion(), "version");
		Validar.obligatorio(receta.getFechaVigencia(), "fecha de vigencia");
		if (receta.getEstado() == null) {
			receta.setEstado(Boolean.TRUE);
		}
		if (productos.buscarPorId(receta.getIdProducto()).isEmpty()) {
			throw new ReglaNegocioException("El producto indicado no existe");
		}

		// la base exige que producto + version no se repitan (uk_receta)
		boolean repetida = recetas.buscarPorProducto(receta.getIdProducto()).stream()
				.filter(otra -> receta.getIdReceta() == null || !receta.getIdReceta().equals(otra.getIdReceta()))
				.anyMatch(otra -> receta.getVersion().equals(otra.getVersion()));
		if (repetida) {
			throw new ReglaNegocioException(
					"Ese producto ya tiene una receta con la version " + receta.getVersion());
		}
	}

	@Override
	public void eliminar(Integer id) {
		// la base borra en cascada el detalle, asi que se avisa antes de que el
		// usuario pierda la formula entera sin darse cuenta
		int lineas = detalles.buscarPorReceta(id).size();
		if (lineas > 0) {
			throw new ReglaNegocioException("No se puede eliminar la receta: todavia tiene " + lineas
					+ " materia(s) prima(s). Quitalas primero.");
		}
		super.eliminar(id);
	}
}
