package com.andiana.api.aplicacion.servicio;

import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.Validar;
import com.andiana.api.dominio.modelo.Receta;
import com.andiana.api.dominio.puerto.ProductoRepositorio;
import com.andiana.api.dominio.puerto.RecetaDetalleRepositorio;
import com.andiana.api.dominio.puerto.RecetaRepositorio;

public class ServicioReceta extends ServicioCrud<Receta> {

	private final RecetaRepositorio recetas;
	private final ProductoRepositorio productos;
	private final RecetaDetalleRepositorio detalles;

	public ServicioReceta(RecetaRepositorio recetas, ProductoRepositorio productos,
			RecetaDetalleRepositorio detalles) {
		super(recetas, "Receta");
		this.recetas = recetas;
		this.productos = productos;
		this.detalles = detalles;
	}

	@Override
	protected void validar(Receta receta) {
		receta.setVersion(Validar.normalizar(receta.getVersion()));

		Validar.obligatorio(receta.getIdProducto(), "producto");
		Validar.obligatorio(receta.getVersion(), "version");
		Validar.obligatorio(receta.getFecha(), "fecha");
		if (receta.getActiva() == null) {
			receta.setActiva(Boolean.TRUE);
		}
		if (productos.buscarPorId(receta.getIdProducto()).isEmpty()) {
			throw new ReglaNegocioException("El producto indicado no existe");
		}

		// dos versiones con el mismo nombre para el mismo producto no se
		// distinguirian al mirar el historial de la formula
		boolean repetida = recetas.buscarPorProducto(receta.getIdProducto()).stream()
				.filter(otra -> receta.getIdReceta() == null || !receta.getIdReceta().equals(otra.getIdReceta()))
				.anyMatch(otra -> receta.getVersion().equalsIgnoreCase(otra.getVersion()));
		if (repetida) {
			throw new ReglaNegocioException(
					"Ese producto ya tiene una receta con la version \"" + receta.getVersion() + "\"");
		}
	}

	@Override
	public void eliminar(Integer id) {
		if (!detalles.buscarPorReceta(id).isEmpty()) {
			throw new ReglaNegocioException(
					"No se puede eliminar la receta: todavia tiene materias primas. Quitalas primero.");
		}
		super.eliminar(id);
	}
}
