package com.andiana.api.aplicacion.casosuso.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.andiana.api.aplicacion.casosuso.entrada.IConsultaUseCase;
import com.andiana.api.dominio.entidades.DetalleReceta;
import com.andiana.api.dominio.entidades.MateriaPrima;
import com.andiana.api.dominio.entidades.Producto;
import com.andiana.api.dominio.entidades.RecetaProduccion;
import com.andiana.api.dominio.repositorio.IDetalleRecetaRepositorio;
import com.andiana.api.dominio.repositorio.IMateriaPrimaRepositorio;
import com.andiana.api.dominio.repositorio.IProductoRepositorio;
import com.andiana.api.dominio.repositorio.IRecetaProduccionRepositorio;
import com.andiana.api.presentacion.dto.response.ConteoRecetaDto;
import com.andiana.api.presentacion.dto.response.MateriaEnRecetaDto;

public class ConsultaUseCaseImpl implements IConsultaUseCase {

	private final IRecetaProduccionRepositorio recetaRepositorio;
	private final IDetalleRecetaRepositorio detalleRepositorio;
	private final IMateriaPrimaRepositorio materiaRepositorio;
	private final IProductoRepositorio productoRepositorio;

	public ConsultaUseCaseImpl(IRecetaProduccionRepositorio recetaRepositorio,
			IDetalleRecetaRepositorio detalleRepositorio, IMateriaPrimaRepositorio materiaRepositorio,
			IProductoRepositorio productoRepositorio) {
		this.recetaRepositorio = recetaRepositorio;
		this.detalleRepositorio = detalleRepositorio;
		this.materiaRepositorio = materiaRepositorio;
		this.productoRepositorio = productoRepositorio;
	}

	@Override
	public List<MateriaEnRecetaDto> materiasDeLaReceta(int idReceta) {
		if (recetaRepositorio.buscarPorid(idReceta).isEmpty()) {
			throw new RuntimeException("La receta indicada no existe");
		}

		Map<Integer, MateriaPrima> porId = new HashMap<>();
		for (MateriaPrima m : materiaRepositorio.listarTodos()) {
			porId.put(m.getIdMateria(), m);
		}

		List<MateriaEnRecetaDto> lineas = new ArrayList<>();
		for (DetalleReceta detalle : detalleRepositorio.buscarPorReceta(idReceta)) {
			MateriaPrima materia = porId.get(detalle.getIdMateria());
			lineas.add(new MateriaEnRecetaDto(detalle.getIdMateria(),
					materia == null ? "(materia prima eliminada)" : materia.getNombre(), detalle.getCantidad(),
					detalle.getUnidad(), materia == null ? null : materia.getStockActual()));
		}
		lineas.sort((a, b) -> a.getMateriaPrima().compareToIgnoreCase(b.getMateriaPrima()));
		return lineas;
	}

	@Override
	public List<ConteoRecetaDto> conteoDeMateriasPorReceta() {
		Map<Integer, String> nombreProducto = new HashMap<>();
		for (Producto p : productoRepositorio.listarTodos()) {
			nombreProducto.put(p.getIdProducto(),
					p.getNombre() + " " + p.getPresentacion() + " (" + p.getVolumenMl() + " ml)");
		}

		Map<Integer, Long> lineasPorReceta = new HashMap<>();
		for (DetalleReceta detalle : detalleRepositorio.listarTodos()) {
			lineasPorReceta.merge(detalle.getIdReceta(), 1L, Long::sum);
		}

		List<ConteoRecetaDto> filas = new ArrayList<>();
		for (RecetaProduccion receta : recetaRepositorio.listarTodos()) {
			filas.add(new ConteoRecetaDto(receta.getIdReceta(),
					nombreProducto.getOrDefault(receta.getIdProducto(), "(producto eliminado)"), receta.getVersion(),
					Boolean.TRUE.equals(receta.getEstado()), lineasPorReceta.getOrDefault(receta.getIdReceta(), 0L)));
		}
		filas.sort((a, b) -> {
			int porProducto = a.getProducto().compareToIgnoreCase(b.getProducto());
			return porProducto != 0 ? porProducto : Integer.compare(a.getVersion(), b.getVersion());
		});
		return filas;
	}
}
