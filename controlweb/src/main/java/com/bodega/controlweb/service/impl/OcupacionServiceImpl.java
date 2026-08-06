package com.bodega.controlweb.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bodega.controlweb.model.dto.response.ContenidoUbicacionDto;
import com.bodega.controlweb.model.dto.response.LoteResponseDto;
import com.bodega.controlweb.model.dto.response.ProductoResponseDto;
import com.bodega.controlweb.model.dto.response.UbicacionResponseDto;
import com.bodega.controlweb.service.ILoteService;
import com.bodega.controlweb.service.IOcupacionService;
import com.bodega.controlweb.service.IProductoService;
import com.bodega.controlweb.service.IUbicacionService;

/**
 * Calcula cuánto espacio ocupa la mercadería realmente almacenada. No existe
 * ningún contador guardado en la base de datos: la ocupación se deduce cada vez
 * sumando las cantidades de los lotes, así que no puede quedar desincronizada
 * cuando entra o sale stock.
 */
@Service
public class OcupacionServiceImpl implements IOcupacionService {

    private final ILoteService servicioLote;
    private final IProductoService servicioProducto;
    private final IUbicacionService servicioUbicacion;

    public OcupacionServiceImpl(ILoteService servicioLote, IProductoService servicioProducto,
            IUbicacionService servicioUbicacion) {
        this.servicioLote = servicioLote;
        this.servicioProducto = servicioProducto;
        this.servicioUbicacion = servicioUbicacion;
    }

    @Override
    public Map<Integer, List<ContenidoUbicacionDto>> contenidoPorUbicacion() {
        Map<Integer, ProductoResponseDto> productos = new HashMap<>();
        for (ProductoResponseDto p : servicioProducto.listarProducto()) {
            productos.put(p.getIdProducto(), p);
        }

        // se agrupa por ubicación y, dentro de cada una, por producto: varios lotes
        // del mismo producto en el mismo sitio se muestran como una sola línea.
        Map<Integer, Map<Integer, Integer>> unidades = new LinkedHashMap<>();
        for (LoteResponseDto lote : servicioLote.listarLote()) {
            if (lote.getIdUbicacion() == null || lote.getIdProducto() == null) {
                continue;
            }
            int cantidad = lote.getCantidadLote() == null ? 0 : lote.getCantidadLote();
            unidades.computeIfAbsent(lote.getIdUbicacion(), k -> new LinkedHashMap<>())
                    .merge(lote.getIdProducto(), cantidad, Integer::sum);
        }

        Map<Integer, List<ContenidoUbicacionDto>> resultado = new HashMap<>();
        unidades.forEach((idUbicacion, porProducto) -> {
            List<ContenidoUbicacionDto> lineas = new ArrayList<>();
            porProducto.forEach((idProducto, cantidad) -> {
                ProductoResponseDto p = productos.get(idProducto);
                lineas.add(new ContenidoUbicacionDto(idProducto,
                        p == null ? null : p.getCodigoProducto(),
                        p == null ? ("Producto #" + idProducto) : p.getNombreProducto(),
                        cantidad));
            });
            lineas.sort(Comparator.comparing(ContenidoUbicacionDto::getNombreProducto,
                    Comparator.nullsLast(String::compareToIgnoreCase)));
            resultado.put(idUbicacion, lineas);
        });
        return resultado;
    }

    @Override
    public Map<Integer, Integer> unidadesPorUbicacion() {
        Map<Integer, Integer> total = new HashMap<>();
        contenidoPorUbicacion().forEach((idUbicacion, lineas) -> total.put(idUbicacion,
                lineas.stream().mapToInt(ContenidoUbicacionDto::getCantidad).sum()));
        return total;
    }

    @Override
    public Map<Integer, Integer> unidadesPorZona() {
        return agruparUbicacionesPor(UbicacionResponseDto::getIdZona);
    }

    @Override
    public Map<Integer, Integer> unidadesPorSede() {
        return agruparUbicacionesPor(UbicacionResponseDto::getIdSede);
    }

    /**
     * Lo reservado sigue ocupando sitio físicamente (aún no ha salido de bodega),
     * pero ya está comprometido, así que se muestra aparte para saber cuánto
     * espacio quedará realmente libre cuando se despache.
     */
    @Override
    public Map<Integer, Integer> reservadasPorSede() {
        Map<Integer, Integer> reservadaPorUbicacion = new HashMap<>();
        for (LoteResponseDto lote : servicioLote.listarLote()) {
            if (lote.getIdUbicacion() == null) {
                continue;
            }
            int reservada = lote.getCantidadReservada() == null ? 0 : lote.getCantidadReservada();
            reservadaPorUbicacion.merge(lote.getIdUbicacion(), reservada, Integer::sum);
        }

        Map<Integer, Integer> resultado = new HashMap<>();
        for (UbicacionResponseDto ubicacion : servicioUbicacion.listarUbicacion()) {
            if (ubicacion.getIdSede() == null) {
                continue;
            }
            resultado.merge(ubicacion.getIdSede(),
                    reservadaPorUbicacion.getOrDefault(ubicacion.getIdUbicacion(), 0), Integer::sum);
        }
        return resultado;
    }

    private Map<Integer, Integer> agruparUbicacionesPor(
            java.util.function.Function<UbicacionResponseDto, Integer> clave) {
        Map<Integer, Integer> porUbicacion = unidadesPorUbicacion();
        Map<Integer, Integer> resultado = new HashMap<>();
        for (UbicacionResponseDto ubicacion : servicioUbicacion.listarUbicacion()) {
            Integer id = clave.apply(ubicacion);
            if (id == null) {
                continue;
            }
            resultado.merge(id, porUbicacion.getOrDefault(ubicacion.getIdUbicacion(), 0), Integer::sum);
        }
        return resultado;
    }
}
