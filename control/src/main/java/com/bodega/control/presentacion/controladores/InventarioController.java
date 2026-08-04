package com.bodega.control.presentacion.controladores;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bodega.control.aplicacion.casosuso.entrada.ILoteUseCase;
import com.bodega.control.aplicacion.casosuso.entrada.IProductoUseCase;
import com.bodega.control.dominio.entidades.Lote;
import com.bodega.control.dominio.entidades.Producto;
import com.bodega.control.presentacion.dto.response.InventarioResponseDto;

/**
 * Vista de solo lectura: cuanto stock hay de cada producto (sumando todos sus
 * lotes) y en cuantos lotes esta repartido. No es una entidad propia, es un
 * agregado calculado sobre Producto + Lote.
 */
@RestController
@RequestMapping("/inventario")
public class InventarioController {

	private final IProductoUseCase productoUseCase;
	private final ILoteUseCase loteUseCase;

	public InventarioController(IProductoUseCase productoUseCase, ILoteUseCase loteUseCase) {
		this.productoUseCase = productoUseCase;
		this.loteUseCase = loteUseCase;
	}

	@GetMapping
	public List<InventarioResponseDto> listar() {
		List<Producto> productos = productoUseCase.listarTodos();
		Map<Integer, List<Lote>> lotesPorProducto = loteUseCase.listarTodos().stream()
				.filter(lote -> lote.getProducto() != null && lote.getProducto().getIdProducto() != null)
				.collect(Collectors.groupingBy(lote -> lote.getProducto().getIdProducto()));

		return productos.stream().map(producto -> {
			List<Lote> lotes = lotesPorProducto.getOrDefault(producto.getIdProducto(), List.of());
			InventarioResponseDto dto = new InventarioResponseDto();
			dto.setIdProducto(producto.getIdProducto());
			dto.setNombreProducto(producto.getNombreProducto());
			dto.setCodigoProducto(producto.getCodigoProducto());
			dto.setNumeroLotes(lotes.size());
			dto.setCantidadTotal(lotes.stream().mapToInt(l -> l.getCantidadLote() == null ? 0 : l.getCantidadLote()).sum());
			dto.setCantidadReservada(
					lotes.stream().mapToInt(l -> l.getCantidadReservada() == null ? 0 : l.getCantidadReservada()).sum());
			dto.setCantidadDisponible(lotes.stream().mapToInt(Lote::getCantidadDisponible).sum());
			return dto;
		}).sorted(Comparator.comparing(InventarioResponseDto::getNombreProducto)).toList();
	}

}
