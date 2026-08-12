package com.bodega.controlweb.service;

import java.util.List;

import com.bodega.controlweb.model.dto.request.ProductoRequestDto;
import com.bodega.controlweb.model.dto.response.ProductoResponseDto;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

public interface IProductoService {

    /** Los que se ven en el sistema: sin los dados de baja. */
    List<ProductoResponseDto> listarProducto();

    /**
     * Todos, incluidos los eliminados. Lo usan las pantallas de historial, que
     * tienen que poder nombrar el producto de un movimiento antiguo.
     */
    List<ProductoResponseDto> listarProductoConEliminados();

    /** Los dados de baja, para poder recuperarlos. */
    List<ProductoResponseDto> listarEliminados();

    /** Devuelve un producto eliminado a los listados. */
    void recuperarProducto(Integer id);

    void guardarProducto(ProductoRequestDto nuevo);

    ProductoResponseDto buscarProductoId(Integer id);

    void eliminarProducto(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
