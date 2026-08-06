package com.bodega.controlweb.service;

import java.util.List;
import java.util.Map;

import com.bodega.controlweb.model.dto.response.ContenidoUbicacionDto;

public interface IOcupacionService {

    /** Productos almacenados en cada ubicación, con su cantidad. */
    Map<Integer, List<ContenidoUbicacionDto>> contenidoPorUbicacion();

    /** Unidades totales almacenadas en cada ubicación. */
    Map<Integer, Integer> unidadesPorUbicacion();

    /** Unidades totales almacenadas en cada zona. */
    Map<Integer, Integer> unidadesPorZona();

    /** Unidades totales almacenadas en cada sede (existencia fisica). */
    Map<Integer, Integer> unidadesPorSede();

    /** Unidades ya reservadas (apartadas para salir) en cada sede. */
    Map<Integer, Integer> reservadasPorSede();
}
