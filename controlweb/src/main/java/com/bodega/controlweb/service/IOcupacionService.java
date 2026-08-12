package com.bodega.controlweb.service;

import java.util.List;
import java.util.Map;

import com.bodega.controlweb.model.dto.response.ContenidoSedeDto;
import com.bodega.controlweb.model.dto.response.ContenidoUbicacionDto;
import com.bodega.controlweb.model.dto.response.LoteResponseDto;

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

    /** Productos guardados en una bodega, con sus unidades y en cuántos lotes vienen. */
    List<ContenidoSedeDto> contenidoPorSede(Integer idSede);

    /** Cuántos lotes hay almacenados en cada bodega. */
    Map<Integer, Integer> lotesPorSede();

    /** Los lotes que hay registrados en una bodega, uno por uno. */
    List<LoteResponseDto> lotesDeLaSede(Integer idSede);
}
