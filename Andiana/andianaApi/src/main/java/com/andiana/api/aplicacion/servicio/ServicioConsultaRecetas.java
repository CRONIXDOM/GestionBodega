package com.andiana.api.aplicacion.servicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.andiana.api.aplicacion.puerto.ConsultaRecetas;
import com.andiana.api.dominio.ReglaNegocioException;
import com.andiana.api.dominio.modelo.MateriaPrima;
import com.andiana.api.dominio.modelo.Producto;
import com.andiana.api.dominio.modelo.Receta;
import com.andiana.api.dominio.modelo.RecetaDetalle;
import com.andiana.api.dominio.puerto.MateriaPrimaRepositorio;
import com.andiana.api.dominio.puerto.ProductoRepositorio;
import com.andiana.api.dominio.puerto.RecetaDetalleRepositorio;
import com.andiana.api.dominio.puerto.RecetaRepositorio;

/**
 * Las consultas se resuelven aqui, en la capa de aplicacion, uniendo lo que
 * devuelven los repositorios. Asi no hacen falta consultas SQL a medida y la
 * base de datos se queda exactamente como esta.
 */
public class ServicioConsultaRecetas implements ConsultaRecetas {

	private final RecetaRepositorio recetas;
	private final RecetaDetalleRepositorio detalles;
	private final MateriaPrimaRepositorio materias;
	private final ProductoRepositorio productos;

	public ServicioConsultaRecetas(RecetaRepositorio recetas, RecetaDetalleRepositorio detalles,
			MateriaPrimaRepositorio materias, ProductoRepositorio productos) {
		this.recetas = recetas;
		this.detalles = detalles;
		this.materias = materias;
		this.productos = productos;
	}

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
			porId.put(m.getIdMateriaPrima(), m);
		}

		List<MateriaEnReceta> lineas = new ArrayList<>();
		for (RecetaDetalle detalle : detalles.buscarPorReceta(idReceta)) {
			MateriaPrima materia = porId.get(detalle.getIdMateriaPrima());
			lineas.add(new MateriaEnReceta(detalle.getIdMateriaPrima(),
					materia == null ? "(materia prima eliminada)" : materia.getNombre(),
					detalle.getCantidad(),
					materia == null ? "" : materia.getUnidadMedida()));
		}
		lineas.sort((a, b) -> a.materiaPrima().compareToIgnoreCase(b.materiaPrima()));
		return lineas;
	}

	@Override
	public List<ConteoDeReceta> conteoDeMateriasPorReceta() {
		Map<Integer, String> nombreProducto = new HashMap<>();
		for (Producto p : productos.listar()) {
			nombreProducto.put(p.getIdProducto(), p.getNombre() + " " + p.getPresentacion());
		}

		// se cuentan las lineas de todas las recetas de una sola pasada, en vez de
		// preguntar por cada receta por separado
		Map<Integer, Long> lineasPorReceta = new HashMap<>();
		for (RecetaDetalle detalle : detalles.listar()) {
			lineasPorReceta.merge(detalle.getIdReceta(), 1L, Long::sum);
		}

		List<ConteoDeReceta> filas = new ArrayList<>();
		for (Receta receta : recetas.listar()) {
			filas.add(new ConteoDeReceta(receta.getIdReceta(),
					nombreProducto.getOrDefault(receta.getIdProducto(), "(producto eliminado)"),
					receta.getVersion(),
					Boolean.TRUE.equals(receta.getActiva()),
					lineasPorReceta.getOrDefault(receta.getIdReceta(), 0L)));
		}
		filas.sort((a, b) -> {
			int porProducto = a.producto().compareToIgnoreCase(b.producto());
			return porProducto != 0 ? porProducto : a.version().compareToIgnoreCase(b.version());
		});
		return filas;
	}
}
