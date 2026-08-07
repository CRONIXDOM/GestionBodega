package com.andiana.api.aplicacion.servicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.andiana.api.aplicacion.puerto.ConsultaRecetas;
import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.modelo.DetalleReceta;
import com.andiana.api.dominio.modelo.MateriaPrima;
import com.andiana.api.dominio.modelo.Producto;
import com.andiana.api.dominio.modelo.RecetaProduccion;
import com.andiana.api.dominio.puerto.DetalleRecetaRepositorio;
import com.andiana.api.dominio.puerto.MateriaPrimaRepositorio;
import com.andiana.api.dominio.puerto.ProductoRepositorio;
import com.andiana.api.dominio.puerto.RecetaProduccionRepositorio;

/**
 * Las consultas se resuelven aqui, en la capa de aplicacion, uniendo lo que
 * devuelven los repositorios. Asi no hacen falta vistas ni consultas SQL a
 * medida y la base de datos se queda exactamente como esta.
 */
public class ServicioConsultaRecetas implements ConsultaRecetas {

	private final RecetaProduccionRepositorio recetas;
	private final DetalleRecetaRepositorio detalles;
	private final MateriaPrimaRepositorio materias;
	private final ProductoRepositorio productos;

	public ServicioConsultaRecetas(RecetaProduccionRepositorio recetas, DetalleRecetaRepositorio detalles,
			MateriaPrimaRepositorio materias, ProductoRepositorio productos) {
		this.recetas = recetas;
		this.detalles = detalles;
		this.materias = materias;
		this.productos = productos;
	}

	/** Consulta 1: las materias primas utilizadas en una receta. */
	@Override
	public List<MateriaEnReceta> materiasDeLaReceta(Integer idReceta) {
		if (idReceta == null) {
			throw new ReglaNegocioException("Indica la receta que quieres consultar");
		}
		if (recetas.buscarPorId(idReceta).isEmpty()) {
			throw new ReglaNegocioException("La receta indicada no existe");
		}

		Map<Integer, MateriaPrima> porId = new HashMap<>();
		for (MateriaPrima m : materias.listar()) {
			porId.put(m.getIdMateria(), m);
		}

		List<MateriaEnReceta> lineas = new ArrayList<>();
		for (DetalleReceta detalle : detalles.buscarPorReceta(idReceta)) {
			MateriaPrima materia = porId.get(detalle.getIdMateria());
			lineas.add(new MateriaEnReceta(detalle.getIdMateria(),
					materia == null ? "(materia prima eliminada)" : materia.getNombre(),
					detalle.getCantidad(),
					detalle.getUnidad(),
					materia == null ? null : materia.getStockActual()));
		}
		lineas.sort((a, b) -> a.materiaPrima().compareToIgnoreCase(b.materiaPrima()));
		return lineas;
	}

	/** Consulta 2: cuantas materias primas lleva cada receta. */
	@Override
	public List<ConteoDeReceta> conteoDeMateriasPorReceta() {
		Map<Integer, String> nombreProducto = new HashMap<>();
		for (Producto p : productos.listar()) {
			nombreProducto.put(p.getIdProducto(),
					p.getNombre() + " " + p.getPresentacion() + " (" + p.getVolumenMl() + " ml)");
		}

		// se cuentan las lineas de todas las recetas de una sola pasada, en vez de
		// preguntar por cada receta por separado
		Map<Integer, Long> lineasPorReceta = new HashMap<>();
		for (DetalleReceta detalle : detalles.listar()) {
			lineasPorReceta.merge(detalle.getIdReceta(), 1L, Long::sum);
		}

		List<ConteoDeReceta> filas = new ArrayList<>();
		for (RecetaProduccion receta : recetas.listar()) {
			filas.add(new ConteoDeReceta(receta.getIdReceta(),
					nombreProducto.getOrDefault(receta.getIdProducto(), "(producto eliminado)"),
					receta.getVersion(),
					Boolean.TRUE.equals(receta.getEstado()),
					lineasPorReceta.getOrDefault(receta.getIdReceta(), 0L)));
		}
		filas.sort((a, b) -> {
			int porProducto = a.producto().compareToIgnoreCase(b.producto());
			return porProducto != 0 ? porProducto : Integer.compare(a.version(), b.version());
		});
		return filas;
	}
}
