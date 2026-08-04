package com.bodega.controlweb.service;

import java.util.List;

import com.bodega.controlweb.model.dto.request.ProductoRequestDto;
import com.bodega.controlweb.model.dto.response.ProductoResponseDto;
import com.bodega.controlweb.model.dto.response.OpcionSelectDto;

public interface IProductoService {

    List<ProductoResponseDto> listarProducto();

    void guardarProducto(ProductoRequestDto nuevo);

    ProductoResponseDto buscarProductoId(Integer id);

    void eliminarProducto(Integer id);

    List<OpcionSelectDto> listarOpciones();
}
