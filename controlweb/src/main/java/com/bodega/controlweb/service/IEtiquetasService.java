package com.bodega.controlweb.service;

import java.util.Map;

import com.bodega.controlweb.model.dto.response.ProductoResponseDto;

/**
 * Traduce los identificadores que devuelve la API a los nombres que el usuario
 * espera ver en pantalla.
 */
public interface IEtiquetasService {

    /** idUsuarioRol → "NOMBRE APELLIDO (ROL)". */
    Map<Integer, String> usuariosConRol();

    /** idProducto → producto completo, para leer nombre, código y unidades por caja. */
    Map<Integer, ProductoResponseDto> productosPorId();

    /** idEntrega → id de la solicitud que atiende esa entrega. */
    Map<Integer, Integer> solicitudPorEntrega();

    /** idSolicitud → "NOMBRE APELLIDO (ROL)" de quien la pidió. */
    Map<Integer, String> solicitantePorSolicitud();

    /** idUbicacion → nombre de la bodega, que es lo que el usuario reconoce. */
    Map<Integer, String> bodegaPorUbicacion();
}
